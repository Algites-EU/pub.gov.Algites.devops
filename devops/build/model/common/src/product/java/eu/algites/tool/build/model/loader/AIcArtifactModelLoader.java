package eu.algites.tool.build.model.loader;

import static eu.algites.tool.build.model.artifact.intf.AIiAbstractControlledArtifact.ARTIFACT_CONFIG_FILE_NAME_WITHOUT_EXT;
import static eu.algites.tool.build.model.artifact.intf.AInArtifactKind.AGGREGATOR;
import static eu.algites.tool.build.model.repository.intf.AIiSourceRepository.SOURCE_REPOSITORY_CONFIG_FILE_NAME_WITHOUT_EXT;
import static eu.algites.tool.build.model.utils.AIsArtifactModelUtils.filenameWithoutExtension;

import eu.algites.tool.build.model.AIcArtifactModel;
import eu.algites.tool.build.model.artifact.intf.AIiAbstractArtifact;
import eu.algites.tool.build.model.artifact.intf.AIiAbstractControlledCoreArtifact;
import eu.algites.tool.build.model.artifact.intf.AIiAbstractControlledParentRwContainerArtifact;
import eu.algites.tool.build.model.artifact.intf.AIiArtifactDependency;
import eu.algites.tool.build.model.artifact.intf.AIiControlledAggregatorArtifact;
import eu.algites.tool.build.model.artifact.intf.AIiAbstractControlledArtifact;
import eu.algites.tool.build.model.artifact.intf.AInArtifactKind;
import eu.algites.tool.build.model.artifact.impl.AIcArtifactDependencyLink;
import eu.algites.tool.build.model.artifact.impl.AIcArtifactDependencyScope;
import eu.algites.tool.build.model.artifact.impl.AIcArtifactProjection;
import eu.algites.tool.build.model.artifact.impl.AIcControlledAggregatorArtifact;
import eu.algites.tool.build.model.artifact.impl.AIcAbstractControlledArtifact;
import eu.algites.tool.build.model.artifact.impl.AIcControlledPolicyArtifact;
import eu.algites.tool.build.model.artifact.impl.AIcControlledPolicyBackgroundBomArtifact;
import eu.algites.tool.build.model.artifact.impl.AIcControlledProductCoreArtifact;
import eu.algites.tool.build.model.artifact.impl.AIcControlledProductInterfaceBomArtifact;
import eu.algites.tool.build.model.artifact.impl.AIcControlledProductVariantBomArtifact;
import eu.algites.tool.build.model.artifact.impl.AIcDependencyScopeBehavior;
import eu.algites.tool.build.model.common.dto.AIcArtifactContainerDefDTO;
import eu.algites.tool.build.model.common.intf.AIiAbstractArtifactContainer;
import eu.algites.tool.build.model.repository.dto.AIcSourceRepositoryDefDTO;
import eu.algites.tool.build.model.repository.impl.AIcSourceRepository;
import eu.algites.tool.build.model.repository.intf.AIiSourceRepository;
import eu.algites.tool.build.model.utils.AInConfigurationFileKind;
import eu.algites.tool.build.model.utils.AIsArtifactModelUtils;
import eu.algites.tool.build.model.version.impl.AIcVersionReleaseLine;
import eu.algites.tool.build.model.version.impl.AIcVersionReleaseLineRevision;
import eu.algites.tool.build.model.artifact.impl.AIcAbstractUncontrolledArtifact;
import eu.algites.tool.build.model.artifact.impl.AIcUncontrolledBomArtifact;
import eu.algites.tool.build.model.artifact.impl.AIcUncontrolledCoreArtifact;
import eu.algites.tool.build.model.version.impl.AIcVersionContext;
import eu.algites.tool.build.model.version.impl.AIcVersionQualifier;
import eu.algites.tool.build.model.artifact.dto.AIcArtifactDefDTO;
import eu.algites.tool.build.model.common.dto.AIcCommonHolderDefDTO;
import eu.algites.tool.build.model.artifact.dto.AIcDependencyDefDTO;
import eu.algites.tool.build.model.artifact.dto.AIcExclusionDefDTO;
import eu.algites.tool.build.model.artifact.dto.AIcScopeDefDTO;
import eu.algites.tool.build.model.version.intf.AIiVersionContext;
import eu.algites.tool.build.model.version.dto.AIcVersionContextDefDTO;
import tools.jackson.core.json.JsonFactory;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.dataformat.yaml.YAMLFactory;
import tools.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Loads the artifact model from YAML into a resolved in-memory graph.
 *
 * <p>ementation note: This loader intentionally keeps "model interfaces" clean of Jackson annotations.
 * YAML is deserialized into DTOs and then converted into the {@code impl} POJOs.</p>
 *
 * @author linhart1
 */
public final class AIcArtifactModelLoader {

	public static final Path VIRTUAL_NONEXISTENT_PATH = Path.of("");

	private static final ThreadLocal<String> artifactConfigurationFileExtension = new ThreadLocal<>();
	private static final ThreadLocal<Deque<AIiVersionContext>> versionContextDeque = ThreadLocal.withInitial(ArrayDeque::new);

	private AIcArtifactModelLoader() {
	}

	/**
	 * Performs the loading of the artifact model repository configuration. Only the contained artifacts will be loaded from
	 * the specified repository configuration file, other artifacts in the repository will be ignored
	 * @param aRepositoryConfigurationFile configuration file for the repository
	 * @return the loaded artifact model
	 * @throws IOException if an error occurs during loading
	 * @throws IllegalArgumentException if during the computation occurred some inconsistency or invalid input from file
	 * @throws IllegalStateException if during the computation occurred some inconsistency or invalid input from file
	 */
	public static AIcArtifactModel loadRepositoryArtifacts(
			Path aRepositoryConfigurationFile) throws IOException, IllegalStateException, IllegalArgumentException {
		if (aRepositoryConfigurationFile == null)
			throw new IllegalArgumentException("No repository configuration file specified.");
		return internalLoad(aRepositoryConfigurationFile, null);
	}


	/**
	 * Performs the loading of the artifact model from the passed files, where the files are intended
	 * to be loaded without any connection to the repository where they are located.
	 * @param aRootAggregatorOrSingleProductArtifactConfigurationFiles configuration files for the artifacts
	 * @return the loaded artifact model
	 * @throws IOException if an error occurs during loading
	 * @throws IllegalArgumentException if during the computation occurred some inconsistency or invalid input from file
	 * @throws IllegalStateException if during the computation occurred some inconsistency or invalid input from file
	 */
	public static AIcArtifactModel loadSpecificArtifactWithoutRepositoryKnown(
			Set<Path> aRootAggregatorOrSingleProductArtifactConfigurationFiles) throws IOException, IllegalStateException, IllegalArgumentException {
		if (aRootAggregatorOrSingleProductArtifactConfigurationFiles == null || aRootAggregatorOrSingleProductArtifactConfigurationFiles.isEmpty())
			throw new IllegalArgumentException("No artifact configuration files specified.");
		return internalLoad(null, aRootAggregatorOrSingleProductArtifactConfigurationFiles);
	}

	/**
	 * Performs the loading of the artifact model from the passed files. The artifact can take the version context
	 * from the repository if it is specified there but ignores the definitions of the contained artifacts
	 * in the repository configuration
	 * @param aRepositoryConfigurationFile configuration file for the repository
	 * @param aRootAggregatorOrSingleProductArtifactConfigurationFiles configuration files for the artifacts to load
	 * @return the loaded artifact model
	 * @throws IOException if an error occurs during loading
	 * @throws IllegalArgumentException if during the computation occurred some inconsistency or invalid input from file
	 * @throws IllegalStateException if during the computation occurred some inconsistency or invalid input from file
	 */
	public static AIcArtifactModel loadSpecificArtifactWithRepositoryVersion(
			Path aRepositoryConfigurationFile,
			Set<Path> aRootAggregatorOrSingleProductArtifactConfigurationFiles) throws IOException, IllegalStateException, IllegalArgumentException {
		if (aRepositoryConfigurationFile == null)
			throw new IllegalArgumentException("No repository configuration file specified.");
		if (aRootAggregatorOrSingleProductArtifactConfigurationFiles == null || aRootAggregatorOrSingleProductArtifactConfigurationFiles.isEmpty())
			throw new IllegalArgumentException("No artifact configuration files specified.");
		return internalLoad(aRepositoryConfigurationFile, aRootAggregatorOrSingleProductArtifactConfigurationFiles);
	}

	/**
	 * Performs the loading of the artifact model from the passed files. The artifacts referenced in the repository configuration
	 * are ignored, only version context can be taken from the repository if specified
	 * @param aRepositoryConfigurationFile configuration file for the repository
	 * @param aRootAggregatorOrSingleProductArtifactConfigurationFileSet configuration files for the artifacts to load
	 * @return the loaded artifact model
	 * @throws IOException if an error occurs during loading
	 */
	private static AIcArtifactModel internalLoad(
			Path aRepositoryConfigurationFile,
			Set<Path> aRootAggregatorOrSingleProductArtifactConfigurationFileSet) throws IOException {
		LinkedHashMap<String, AIcInternalArtifactLoadingContainer> locArtifactsByIdMap = new LinkedHashMap<>();
		LinkedHashMap<Path, AIiAbstractControlledArtifact> locArtifactsToFinalize = new LinkedHashMap<>();

		boolean locArtifactDefinitionsMandatory = aRepositoryConfigurationFile == null;

		AIiSourceRepository locSourceRepository = null;

		if (aRepositoryConfigurationFile != null) {
			if (!SOURCE_REPOSITORY_CONFIG_FILE_NAME_WITHOUT_EXT.equals(filenameWithoutExtension(aRepositoryConfigurationFile)))
				throw new IllegalArgumentException("Repository configuration file must have name '" + SOURCE_REPOSITORY_CONFIG_FILE_NAME_WITHOUT_EXT
						+ "', the file final name is invalid in path '" + aRepositoryConfigurationFile + "'.");
			final Path locRepositoryConfigurationPath = AIsArtifactModelUtils.resolveConfigFilePath(
					SOURCE_REPOSITORY_CONFIG_FILE_NAME_WITHOUT_EXT,
					AInConfigurationFileKind.getDefaultKind().getDefaultExtension(),
					aRepositoryConfigurationFile.getParent(),
					aPath -> { });
			if (locRepositoryConfigurationPath != null) {
				AIcCommonHolderDefDTO locRepositoryDefDTO = loadDef(locRepositoryConfigurationPath);
				locSourceRepository = initSourceRepository(
						locArtifactsByIdMap,
						locRepositoryDefDTO,
						locRepositoryConfigurationPath,
						aRootAggregatorOrSingleProductArtifactConfigurationFileSet == null);
				locArtifactsToFinalize.putAll(locSourceRepository.getContainedArtifacts());
			} else {
				throw new IllegalArgumentException("Repository configuration file '" + aRepositoryConfigurationFile + "' could not be used for loading of repository.");
			}
		}

		/* Load the artifacts: */
		for (Path locArtifactPath : aRootAggregatorOrSingleProductArtifactConfigurationFileSet) {
			AIcCommonHolderDefDTO locRootArtifactDefDTO = loadDef(locArtifactPath);
			initArtifactDef(locArtifactsByIdMap, locRootArtifactDefDTO, locArtifactPath,
					true, null);
			AIiAbstractControlledArtifact locRootArtifact
					= (AIiAbstractControlledArtifact) locArtifactsByIdMap.get(
							locRootArtifactDefDTO.getArtifactDefDTO().getCoordinatedId()).artifact;
			/* cover single core artifacts: */
			if (locRootArtifact.getArtifactKind().isCoreFunctionalityArtifact()
					&& ((AIiAbstractControlledCoreArtifact)locRootArtifact).isSingleModeCoreArtifact()) {
				locRootArtifact
						= (AIiControlledAggregatorArtifact) locArtifactsByIdMap.get(
								AIsArtifactModelUtils.resolveArtificialArtifactId(
										locRootArtifact.getCoordinatedId(),
										AGGREGATOR)).artifact;
			}

			if (locRootArtifact instanceof AIiControlledAggregatorArtifact) {
				AIiAbstractControlledArtifact locAlreadyExistingArtifact
						= locArtifactsToFinalize.get(locArtifactPath);
				if (locAlreadyExistingArtifact == null)
					locArtifactsToFinalize.put(locArtifactPath, locRootArtifact);
				else
					if (!locAlreadyExistingArtifact.equals(locRootArtifact)) {
						throw new IllegalStateException("\\\\ Development error - Artifact resolved twice from the same file '" + locArtifactPath
								+ "', but with different result!"
								+ "\nAlready loaded artifact: " + locAlreadyExistingArtifact
								+ "\nNewly loaded artifact: " + locRootArtifact);
					}
			}
		  else
		  	throw new IllegalStateException("\\\\ Development error: Artifact '" + locRootArtifactDefDTO.getArtifactDefDTO().getCoordinatedId() + "' from file '"
			  		+ aRootAggregatorOrSingleProductArtifactConfigurationFileSet + "' is not an aggregator artifact as well as not single core artifact.");

		}

		final Collection<AIiAbstractControlledArtifact> locFinalArtifactList = locArtifactsToFinalize.values();
		resolveArtifactReferences(locArtifactsByIdMap, locFinalArtifactList);
		final AIcArtifactModel locResult
				= new AIcArtifactModel(
						locSourceRepository,
						locArtifactsByIdMap.entrySet().stream().map(
								(Function<Map.Entry<String, AIcInternalArtifactLoadingContainer>, Map.Entry<String, AIiAbstractArtifact>>)
								aLoaderEntry -> new AbstractMap.SimpleEntry<>(aLoaderEntry.getKey(), aLoaderEntry.getValue().artifact))
								.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
		return locResult;
	}

	private static AIiSourceRepository initSourceRepository(
			final LinkedHashMap<String, AIcInternalArtifactLoadingContainer> aArtifactsByIdMap, final AIcCommonHolderDefDTO aRepositoryDefDTO,
			final Path aRepositoryConfigurationFile, final boolean aLoadRepositoryArtifacts) throws IOException {
		AIcSourceRepository locResult = new AIcSourceRepository();
		locResult.setRepositoryConfigurationFile(aRepositoryConfigurationFile);
		AIcSourceRepositoryDefDTO locSourceRepositoryDTO = aRepositoryDefDTO.getSourceRepositoryDefDTO();
		locResult.setId(locSourceRepositoryDTO.getRepositoryId());
		locResult.setName(locSourceRepositoryDTO.getDisplayName());
		locResult.setDescription(locSourceRepositoryDTO.getDescription());
		locResult.setVersionContext(resolveVersionContext(aRepositoryDefDTO.getVersionContextDefDTO(), "repoisitory " + locSourceRepositoryDTO.getRepositoryId()));
		if (aLoadRepositoryArtifacts) {
			/* Load all aggregator artifacts and create shells for them: */
			AIcArtifactContainerDefDTO locArtifactContainerDefDTO = aRepositoryDefDTO.getSourceRepositoryDefDTO();
			final Consumer<Path> locPathChecker = aPath -> {
				if (aPath == null || locResult.getContainedArtifacts().containsKey(aPath))
					throw new IllegalArgumentException("Repository '" + locResult.getId() + "' in file '"
							+ aRepositoryConfigurationFile + "' contains " + (aPath == null ? "invalid/non-existent" : "duplicate") + " artifact path '"
							+ aPath + "'");
			};
			loadContainedArtifacts(
					aArtifactsByIdMap,
					locResult, aRepositoryDefDTO.getSourceRepositoryDefDTO(), aRepositoryDefDTO,
					SOURCE_REPOSITORY_CONFIG_FILE_NAME_WITHOUT_EXT, aRepositoryConfigurationFile,
					aPath -> {
						if (aPath == null || locResult.getContainedArtifacts().containsKey(aPath))
							throw new IllegalArgumentException("Repository '" + locResult.getId() + "' in file '"
									+ aRepositoryConfigurationFile + "' contains " + (aPath == null ? "invalid/non-existent" : "duplicate") + " artifact path '"
									+ aPath + "'");
					}
			);

		}
		return locResult;
	}

	private static class AIcInternalArtifactLoadingContainer {
		private final AIiAbstractArtifact artifact;
		private final AIcArtifactDefDTO artifactDefDTO;

		private AIcInternalArtifactLoadingContainer(final AIiAbstractArtifact aArtifact, final AIcArtifactDefDTO aArtifactDefDTO) {
			artifact = aArtifact;
			artifactDefDTO = aArtifactDefDTO;
		}

		/**
		 * @return the artifacts
		 */
		public AIiAbstractArtifact getArtifact() {
			return artifact;
		}

		/**
		 * @return the artifactDefDTO
		 */
		public AIcArtifactDefDTO getArtifactDefDTO() {
			return artifactDefDTO;
		}
	}

	private static AIcCommonHolderDefDTO loadDef(
			Path aDefConfigurationFile) throws IOException {
		try (InputStream locInputStream = Files.newInputStream(aDefConfigurationFile)) {
			/*Jackson 3: mappers are immutable; use the builder to configure features */
			ObjectMapper mapper;
			switch (AInConfigurationFileKind.fromPath(aDefConfigurationFile)) {
			case YAML:
				mapper = YAMLMapper.builder(new YAMLFactory())
						.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
						.build();
				break;
			case JSON:
				mapper = JsonMapper.builder(new JsonFactory())
						.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
						.build();
				break;
			default:
				throw new IllegalArgumentException("Unrecognized artifact source: " + aDefConfigurationFile);
			}
			AIcCommonHolderDefDTO locLoadedDefinition = mapper.readValue(locInputStream, AIcCommonHolderDefDTO.class);
			return locLoadedDefinition;
		}
	}

	private static void initArtifactDef(
			LinkedHashMap<String, AIcInternalArtifactLoadingContainer> aArtifactsByIdMap,
			AIcCommonHolderDefDTO aDefHolder,
			Path aConfigurationFile,
			final boolean aFirstRunAllowingSingleCoreArtifactInitialization,
			final AInArtifactKind aInitializingArtificialArtifactOfKind)
			throws IOException {
		if (aDefHolder == null || aDefHolder.getArtifactDefDTO() == null) {
			return;
		}

		if (aFirstRunAllowingSingleCoreArtifactInitialization
				&& aInitializingArtificialArtifactOfKind == null
				&& (!aDefHolder.getArtifactDefDTO().getKind().isCoreFunctionalityArtifact()
				    || aDefHolder.getArtifactDefDTO().getKind() == AGGREGATOR)) {
			throw new IllegalStateException("Cannot loader artifact '" + aDefHolder.getArtifactDefDTO().getCoordinatedId()
					+ "' in file '" + aConfigurationFile + "' if it is not an aggregator or core artifact in single artifact mode.");
		}

		/* 0) Initialize version context */
		boolean locArtifactWithNewVersionContextPushed = false;
		if (aDefHolder.getVersionContextDefDTO() == null) {
			if (versionContextDeque.get().peek() == null)
				throw new IllegalArgumentException(
						"Cannot loade artifact '" + aDefHolder.getArtifactDefDTO().getCoordinatedId() + "' in file '" + aConfigurationFile
								+ "' if no version context is defined. The version context can be defined:\n"
								+ "1. on the repository level\n"
								+ "2. in the aggregator artifact\n"
								+ "3. In the single core artifact\n"
								+ "but never inside of the non-aggregator artifact contained within some existing aggregator artifact.");
		} else {
			if (aFirstRunAllowingSingleCoreArtifactInitialization
					&& aDefHolder.getArtifactDefDTO().getKind().isCoreFunctionalityArtifact()
					&& aInitializingArtificialArtifactOfKind == null
					|| aDefHolder.getArtifactDefDTO().getKind() == AGGREGATOR) {
				versionContextDeque.get().push(
						resolveVersionContext(aDefHolder.getVersionContextDefDTO(), "artifact " + aDefHolder.getArtifactDefDTO().getCoordinatedId()));
				locArtifactWithNewVersionContextPushed = true;
			}
		}
		try {
			/* 1) Instantiate artifact (without links) */
			initializeControlledArtifactShell(aDefHolder.getArtifactDefDTO(), aArtifactsByIdMap, aInitializingArtificialArtifactOfKind);
			AIcAbstractControlledArtifact locControlledArtifact =
					aInitializingArtificialArtifactOfKind == null
							? (AIcAbstractControlledArtifact) aArtifactsByIdMap.get(aDefHolder.getArtifactDefDTO().getCoordinatedId()).artifact
					    : (AIcAbstractControlledArtifact) aArtifactsByIdMap.get(AIsArtifactModelUtils.resolveArtificialArtifactId(aDefHolder.getArtifactDefDTO().getCoordinatedId(),
									aInitializingArtificialArtifactOfKind)).artifact;
			if (aDefHolder.getArtifactDefDTO().getKind() == AGGREGATOR) {
				AIcControlledAggregatorArtifact locAggregatorArtifact = (AIcControlledAggregatorArtifact) aArtifactsByIdMap.get(aDefHolder.getArtifactDefDTO().getCoordinatedId()).artifact;
				locAggregatorArtifact.setContainedArtifacts(new HashMap<>());
				if (aInitializingArtificialArtifactOfKind != null) {
					if (aInitializingArtificialArtifactOfKind != AGGREGATOR)
						throw new IllegalStateException("\\\\ Development error - artificial aggregator has invalid kind '" + aInitializingArtificialArtifactOfKind
								+ " for single artifact '" + aDefHolder.getArtifactDefDTO().getCoordinatedId());
					final AIiAbstractArtifact locSingleCoreArtifact = aArtifactsByIdMap.get(aDefHolder.getArtifactDefDTO().getCoordinatedId()).artifact;
					if (locSingleCoreArtifact instanceof AIiAbstractControlledArtifact locContainedArtifact) {
						locAggregatorArtifact.getContainedArtifacts().put(locContainedArtifact.getArtifactConfigurationFile(), locContainedArtifact);
					}
				} else {
					/* Load all aggregator artifacts and create shells for them: */
					loadContainedArtifacts(
							aArtifactsByIdMap,
							locAggregatorArtifact, aDefHolder.getArtifactDefDTO(), aDefHolder,
							ARTIFACT_CONFIG_FILE_NAME_WITHOUT_EXT, aConfigurationFile,
							aPath -> {
								if (aPath == null || locAggregatorArtifact.getContainedArtifacts().containsKey(aPath))
									throw new IllegalArgumentException("Aggregator '" + aDefHolder.getArtifactDefDTO().getCoordinatedId() + "' in file '"
											+ aConfigurationFile + "' contains " + (aPath == null ? "invalid/non-existent" : "duplicate") + " artifact path '"
											+ aPath + "'");
							}
					);
				}
			}
			/* finalize the basic artifact initialization: */
			locControlledArtifact.setArtifactConfigurationFile(aConfigurationFile);
			locControlledArtifact.setVersionContext(versionContextDeque.get().peek());
			if (aFirstRunAllowingSingleCoreArtifactInitialization && aDefHolder.getArtifactDefDTO().getKind().isCoreFunctionalityArtifact()) {
				/* Create the artificial policy and aggregator artifact for a new core artifact in single-artifact-mode: */
				initSingleCoreArtifactPolicyAndAggregator(aArtifactsByIdMap, aDefHolder, locControlledArtifact);
			}

		} finally {
			if (locArtifactWithNewVersionContextPushed)
				versionContextDeque.get().pop();
		}
	}

	private static void loadContainedArtifacts(
			final LinkedHashMap<String, AIcInternalArtifactLoadingContainer> aArtifactsByIdMap,
			final AIiAbstractArtifactContainer aArtifactContainer, final AIcArtifactContainerDefDTO aArtifactContainerDefDTO, final AIcCommonHolderDefDTO aDefHolder,
			final String aConfigFileNameWithoutExtension, final Path aConfigurationFile,
			final Consumer<Path> aPathChecker) throws IOException {
		for (String locContainedArtifactPathString : aArtifactContainerDefDTO.getContainedArtifactRelativePaths()) {
			Path locContainedArtifactFolder = aConfigurationFile.getParent().resolve(locContainedArtifactPathString);
			final Path locContainedArtifactPath = AIsArtifactModelUtils.resolveConfigFilePath(
					aConfigFileNameWithoutExtension,
					AInConfigurationFileKind.getDefaultKind().getDefaultExtension(),
					locContainedArtifactFolder,
					aPathChecker);

			AIcCommonHolderDefDTO locArtifactDefDTO = loadDef(
					aConfigurationFile);
			initArtifactDef(
					aArtifactsByIdMap, locArtifactDefDTO, aConfigurationFile,
					false, null);
			final AIiAbstractArtifact locCreatedArtifact = aArtifactsByIdMap.get(locArtifactDefDTO.getArtifactDefDTO().getCoordinatedId()).artifact;
			if (locCreatedArtifact instanceof AIiAbstractControlledArtifact locContainedArtifact) {
				if (aArtifactContainer.getContainedArtifacts().containsKey(locContainedArtifactPath))
					throw new IllegalStateException(
							"Aggregator '" + aDefHolder.getArtifactDefDTO().getCoordinatedId() + "' in file '" + locContainedArtifactPath + "'.");
				aArtifactContainer.getContainedArtifacts().put(locContainedArtifactPath, locContainedArtifact);
			}
		}
	}

	private static void initSingleCoreArtifactPolicyAndAggregator(
			final LinkedHashMap<String, AIcInternalArtifactLoadingContainer> aArtifactsByIdMap,
			final AIcCommonHolderDefDTO aSingleCoreArtifactDefHolder, final AIcAbstractControlledArtifact aSingleCoreArtifact) {
		//@TODO CHATGPT Implement initSingleCoreArtifactPolicyAndAggregator
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	private static void initializeControlledArtifactShell(
			final AIcArtifactDefDTO aArtifactDefDTO,
			final Map<String, AIcInternalArtifactLoadingContainer> byId, final AInArtifactKind aInitializingArtificialArtifactOfKind) {
		require(aArtifactDefDTO.getKind(), "artifact.kind");
		require(aArtifactDefDTO.getGroupId(), "artifact.groupId");
		require(aArtifactDefDTO.getArtifactIdBase(), "artifact.artifactIdBase");

		final String locId =
				aInitializingArtificialArtifactOfKind == null
						? aArtifactDefDTO.getCoordinatedId()
						: AIsArtifactModelUtils.resolveArtificialArtifactId(aArtifactDefDTO.getCoordinatedId(), aInitializingArtificialArtifactOfKind);

		if (byId.containsKey(locId)) {
			throw new IllegalArgumentException("Duplicate artifact id: " + locId);
		}

		AIiAbstractArtifact created = createControlledArtifactShell(aArtifactDefDTO, aInitializingArtificialArtifactOfKind);
		byId.put(locId, new AIcInternalArtifactLoadingContainer(created, aArtifactDefDTO));
	}

	private static void resolveArtifactReferences(LinkedHashMap<String, AIcInternalArtifactLoadingContainer> aArtifactsByIdMap,
			Collection<AIiAbstractControlledArtifact> aArtifactsToProcess) {

		/* 2) Resolve cross-references and dependencies */
		for (AIiAbstractArtifact owner : aArtifactsToProcess) {
			AIcInternalArtifactLoadingContainer locArtifactContainer = aArtifactsByIdMap.get(owner.getCoordinatedId());
			AIcArtifactDefDTO locArtifactDefDTO = locArtifactContainer.getArtifactDefDTO();

			/* parent */
			if (locArtifactDefDTO.getParentId() != null) {
				AIiAbstractArtifact parent = requireRef(
						aArtifactsByIdMap,
						locArtifactDefDTO.getParentId(),
						"parent of " + locArtifactDefDTO.getCoordinatedId());
				setParent((AIiAbstractControlledParentRwContainerArtifact) owner, parent);
			}

			/* artifact-kind specific cross-references */
			if (owner instanceof AIcControlledProductInterfaceBomArtifact) {
				if (locArtifactDefDTO.getPolicyBackgroundBomId() != null) {
					AIiAbstractArtifact pb = requireRef(
							aArtifactsByIdMap,
							locArtifactDefDTO.getPolicyBackgroundBomId(),
							"policyBackgroundBom of " + locArtifactDefDTO.getCoordinatedId());
					((AIcControlledProductInterfaceBomArtifact) owner).setPolicyBackgroundBom((AIcControlledPolicyBackgroundBomArtifact) pb);
				}
			}

			if (owner instanceof AIcControlledProductVariantBomArtifact) {
				if (locArtifactDefDTO.getProductInterfaceBomId() != null) {
					AIiAbstractArtifact pib = requireRef(
							aArtifactsByIdMap,
							locArtifactDefDTO.getProductInterfaceBomId(),
							"productInterfaceBom of " + locArtifactDefDTO.getCoordinatedId());
					((AIcControlledProductVariantBomArtifact) owner).setProductInterfaceBom((AIcControlledProductInterfaceBomArtifact) pib);
				}
			}

			if (owner instanceof AIcControlledAggregatorArtifact) {
				/* Already resolved in 1 */
			}

			// dependencies
			setDeps(owner, locArtifactDefDTO, aArtifactsByIdMap);
		}

	}

	private static void setDeps(AIiAbstractArtifact owner, AIcArtifactDefDTO a, Map<String, AIcInternalArtifactLoadingContainer> byId) {
		if (owner instanceof AIcControlledPolicyArtifact) {
			((AIcControlledPolicyArtifact) owner).setDirectDependencies(resolveDeps(a.getDirectDependencies(), byId));
			((AIcControlledPolicyArtifact) owner).setManagedPolicyBackgroundDependencies(resolveDeps(
					a.getManagedPolicyBackgroundDependencies(),
					byId));
		}

		if (owner instanceof AIcControlledProductCoreArtifact) {
			((AIcControlledProductCoreArtifact) owner).setDirectDependencies(resolveDeps(a.getDirectDependencies(), byId));
		}

		if (owner instanceof AIcControlledPolicyBackgroundBomArtifact) {
			((AIcControlledPolicyBackgroundBomArtifact) owner).setManagedPolicyBackgroundDependencies(resolveDeps(
					a.getManagedPolicyBackgroundDependencies(),
					byId));
		}

		if (owner instanceof AIcControlledProductInterfaceBomArtifact) {
			((AIcControlledProductInterfaceBomArtifact) owner).setManagedInterfaceDependencies(resolveDeps(
					a.getManagedInterfaceDependencies(),
					byId));
		}

		if (owner instanceof AIcControlledProductVariantBomArtifact) {
			((AIcControlledProductVariantBomArtifact) owner).setManagedProductVariantDependencies(resolveDeps(
					a.getManagedProductVariantDependencies(),
					byId));
		}
	}

	@SuppressWarnings("unchecked")
	private static <T extends AIiAbstractArtifact> List<AIiArtifactDependency<? extends T>> resolveDeps(
			List<AIcDependencyDefDTO> deps, Map<String, AIcInternalArtifactLoadingContainer> byId) {

		if (deps == null)
			return null;

		List<AIiArtifactDependency<? extends T>> out = new ArrayList<>();
		for (AIcDependencyDefDTO d : deps) {
			AIiAbstractArtifact target = requireRef(byId, d.getTargetId(), "dependency.target");
			AIcArtifactDependencyLink<T> dep = new AIcArtifactDependencyLink<>();
			dep.setLinkedArtifact((T) target);
			dep.setDependencyScope(resolveScope(d.getScope()));

			if (d.getExclusions() != null) {
				for (AIcExclusionDefDTO ex : d.getExclusions()) {
					AIiAbstractArtifact exTarget = requireRef(byId, ex.getTargetId(), "dependency.exclusions.target");
					AIcArtifactProjection<AIiAbstractArtifact> proj = new AIcArtifactProjection<>();
					proj.setLinkedArtifact(exTarget);
					proj.setOutputClassifier(ex.getOutputClassifier());
					proj.setOutputTypeId(ex.getOutputTypeId());
					dep.addDependencyExclusion(proj);
				}
			}

			out.add(dep);
		}
		return out;
	}

	private static AIcArtifactDependencyScope resolveScope(AIcScopeDefDTO scope) {
		if (scope == null)
			return null;

		AIcDependencyScopeBehavior behavior = new AIcDependencyScopeBehavior(
				Boolean.TRUE.equals(scope.getLocked()),
				Boolean.TRUE.equals(scope.getTransitive())
		);

		return new AIcArtifactDependencyScope(scope.getLevel(), behavior);
	}

	private static AIiAbstractArtifact createControlledArtifactShell(AIcArtifactDefDTO aArtifactDefDTO, final AInArtifactKind aInitializingArtificialArtifactOfKind) {
		AInArtifactKind locArtifactKind =
		  aInitializingArtificialArtifactOfKind == null
		  ? aArtifactDefDTO.getKind()
				: aInitializingArtificialArtifactOfKind;
		final String locArtifactIdBase =
				aInitializingArtificialArtifactOfKind == null
						? aArtifactDefDTO.getArtifactIdBase()
						  : AIsArtifactModelUtils.resolveArtificialArtifactId(aArtifactDefDTO.getArtifactIdBase(), aInitializingArtificialArtifactOfKind);

		if (!locArtifactKind.isControlled())
			throw new IllegalArgumentException("\\\\Development error: Uncontrolled artifact kind: " + locArtifactKind + " for artifact with Id "
					+ aArtifactDefDTO.getCoordinatedId() + "cannot be used for creation of uncontrolled Artifact shell");

		AIcAbstractControlledArtifact base = new AIcAbstractControlledArtifact() {
		};
		base.setArtifactGroupId(aArtifactDefDTO.getGroupId());
		base.setArtifactIdBase(locArtifactIdBase);
		base.setArtifactKind(locArtifactKind);

		switch (locArtifactKind) {
		case POLICY: {
			AIcControlledPolicyArtifact p = new AIcControlledPolicyArtifact();
			copyControlledBase(base, p);
			p.setPolicyDefinitionUid(aArtifactDefDTO.getPolicyDefinitionUid());
			p.setPolicyDefinitionVersion(aArtifactDefDTO.getPolicyDefinitionVersion());
			return p;
		}
		case POLICY_BACKGROUND_BOM: {
			AIcControlledPolicyBackgroundBomArtifact pb = new AIcControlledPolicyBackgroundBomArtifact();
			copyControlledBase(base, pb);
			return pb;
		}
		case PRODUCT_CORE: {
			AIcControlledProductCoreArtifact pc = new AIcControlledProductCoreArtifact();
			copyControlledBase(base, pc);
			return pc;
		}
		case PRODUCT_INTERFACE_BOM: {
			AIcControlledProductInterfaceBomArtifact pi = new AIcControlledProductInterfaceBomArtifact();
			copyControlledBase(base, pi);
			return pi;
		}
		case PRODUCT_VARIANT_BOM: {
			AIcControlledProductVariantBomArtifact pv = new AIcControlledProductVariantBomArtifact();
			copyControlledBase(base, pv);
			return pv;
		}
		case AGGREGATOR: {
			AIcControlledAggregatorArtifact ag = new AIcControlledAggregatorArtifact();
			copyControlledBase(base, ag);
			return ag;
		}
		default:
			throw new IllegalArgumentException("Unknown artifact kind: " + locArtifactKind + " for artifact with Id " + aArtifactDefDTO.getCoordinatedId());
		}
	}

	private static AIiAbstractArtifact createUncontrolledArtifactShell(AIcArtifactDefDTO a, final AInArtifactKind aInitializingArtificialArtifactOfKind) {
		AInArtifactKind locArtifactKind =
				aInitializingArtificialArtifactOfKind == null
						? a.getKind()
						: aInitializingArtificialArtifactOfKind;
		final String locArtifactIdBase =
				aInitializingArtificialArtifactOfKind == null
						? a.getArtifactIdBase()
						: AIsArtifactModelUtils.resolveArtificialArtifactId(a.getArtifactIdBase(), aInitializingArtificialArtifactOfKind);

		// intf

		if (locArtifactKind.isControlled())
			throw new IllegalArgumentException("\\\\Development error: Controlled artifact kind: " + locArtifactKind + " for artifact with Id "
					+ a.getCoordinatedId() + "cannot be used for creation of uncontrolled Artifact shell");
		if (locArtifactKind == AInArtifactKind.UNCONTROLLED_CORE || locArtifactKind == AInArtifactKind.UNCONTROLLED_BOM) {
			require(a.getVersion(), "artifact.version (uncontrolled)");
			AIcAbstractUncontrolledArtifact u = locArtifactKind == AInArtifactKind.UNCONTROLLED_CORE ?
					new AIcUncontrolledCoreArtifact() : new AIcUncontrolledBomArtifact();
			u.setArtifactGroupId(a.getGroupId());
			u.setArtifactIdBase(locArtifactIdBase);
			u.setArtifactKind(locArtifactKind);
			u.setArtifactVersion(a.getVersion());
			return u;
		}
		throw new IllegalArgumentException("Unknown artifact kind: " + locArtifactKind + " for artifact with Id " + a.getCoordinatedId());

	}

	private static void copyControlledBase(AIcAbstractControlledArtifact src, AIcAbstractControlledArtifact dst) {
		dst.setArtifactGroupId(src.getArtifactGroupId());
		dst.setArtifactIdBase(src.getArtifactIdBase());
		dst.setArtifactKind(src.getArtifactKind());
		dst.setVersionContext(src.getVersionContext());
	}

	private static AIcVersionContext resolveVersionContext(AIcVersionContextDefDTO vc, String artifactId) {
		if (vc == null) {
			// some kinds may not require a version context in early stages
			return null;
		}
		Objects.requireNonNull(vc.getReleaseLine(), "versionContext.releaseLine for " + artifactId);
		Objects.requireNonNull(vc.getQualifierKind(), "versionContext.qualifierKind for " + artifactId);
		Objects.requireNonNull(vc.getQualifierLabel(), "versionContext.qualifierLabel for " + artifactId);

		return new AIcVersionContext(
				new AIcVersionReleaseLine(vc.getReleaseLine()),
				new AIcVersionReleaseLineRevision(vc.getRevision()),
				new AIcVersionQualifier(vc.getQualifierKind(), vc.getQualifierLabel())
		);
	}

	@SuppressWarnings("unchecked")
	private static void setParent(AIiAbstractControlledParentRwContainerArtifact aOwner, AIiAbstractArtifact aParent) {
		aOwner.setParent(aParent);
	}

	private static AIiAbstractArtifact requireRef(Map<String, AIcInternalArtifactLoadingContainer> byId, String id, String what) {
		require(id, what);
		AIcInternalArtifactLoadingContainer locAlc = byId.get(id);
		if (locAlc == null) {
			throw new IllegalArgumentException("Unknown artifact reference '" + id + "' (" + what + ")");
		}
		return locAlc.artifact;
	}

	private static void require(Object value, String what) {
		if (value == null)
			throw new IllegalArgumentException("Missing required value: " + what);
		if (value instanceof String && ((String) value).trim().isEmpty()) {
			throw new IllegalArgumentException("Missing required value: " + what);
		}
	}

}
