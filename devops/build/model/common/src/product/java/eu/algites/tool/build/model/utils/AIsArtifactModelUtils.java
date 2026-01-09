package eu.algites.tool.build.model.utils;

import static eu.algites.tool.build.model.artifact.intf.AIiAbstractControlledArtifact.ARTIFACT_CONFIG_FILE_NAME_WITHOUT_EXT;

import eu.algites.tool.build.model.artifact.intf.AInArtifactKind;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

/**
 * <p>
 * Title: {@link AIsArtifactModelUtils}
 * </p>
 * <p>
 * Description: Utilities for the artifact handling
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 09.01.26 10:43
 */
public class AIsArtifactModelUtils {
	private AIsArtifactModelUtils() {
	}

	/**
	 * Performs the resolution of the normalized coordinated Identification
	 * of an artifact.
	 * @param aArtifactGroupId groupId of the artifact
	 * @param aArtifactBaseId baseId of the artifact
	 * @return the normalized coordinated Identification of the artifact
	 */
	public static String getArtifactCoordinatedId(String aArtifactGroupId, String aArtifactBaseId) {
		return aArtifactGroupId + ":" + aArtifactBaseId;
	}

	/**
	 * Resolves the filename without extension from the given path
	 * @param aPath path to the file
	 * @return filename without extension
	 */
	public static String filenameWithoutExtension(Path aPath) {
		String name = aPath.getFileName().toString();
		int dot = name.lastIndexOf('.');
		return (dot > 0) ? name.substring(0, dot) : name;
	}

	/**
	 * Resolves the normalized form of the artificial artifact for the Single Core Artifacts
	 * @param aSingleCoreArtifactId artifactId or DTO ID of the artifact
	 * @param aArtificialArtifactKind kind of artificial artifact to be delivered
	 * @return normalized form of the artificial artifact
	 * @throws IllegalArgumentException if the passed argument for the artificial argument is null
	 *    or does not fulfill the condition {@link AInArtifactKind#isPotentialArtificialArtifact()}
	 */
	public static String resolveArtificialArtifactId(final String aSingleCoreArtifactId, final AInArtifactKind aArtificialArtifactKind)
			throws IllegalArgumentException {
		if (aArtificialArtifactKind == null || !aArtificialArtifactKind.isPotentialArtificialArtifact())
			throw new IllegalArgumentException("Artificial artifact kind must not be null and must be a potential artificial artifact. Passed value: "
					+ aArtificialArtifactKind + " does not fulfill this condition for the passed aSingleCoreArtifactId:" + aSingleCoreArtifactId);
		return aSingleCoreArtifactId + "-artificial-" + aArtificialArtifactKind.name();
	}

	/**
	 * For the given file name without extension and preferred extension in the give file folder resolves
	 * the supported configuration file according to the file extension and if such file does not exist,
	 * it tests also the other supported extensions and file types.
	 * @param aConfigFileWithoutExt name of the configuration file without extension
	 * @param aPreferredConfigFileExtension preferred configuration extension, which should be tested as the first one
	 * @param aConfigFileFolder folder where the configuration file has to be searched
	 * @param aPathChecker checker of the path resolved if it is a correct and possible path for a given method call.
	 *    If the checker fails, then it should throw the runtime exception, which should then capture the
	 *    caller of this method
	 * @return path to the existent configuration file
	 */
	public static Path resolveConfigFilePath(
			final String aConfigFileWithoutExt,
			final String aPreferredConfigFileExtension,
			final Path aConfigFileFolder,
			final Consumer<Path> aPathChecker) {
		Path locContainedArtifactPath = aConfigFileFolder.resolve(
				aConfigFileWithoutExt + "." + aPreferredConfigFileExtension);
		aPathChecker.accept(locContainedArtifactPath);
		AtomicReference<List<String>> locTestedExtensions = new AtomicReference<>();
		while (locContainedArtifactPath != null && Files.notExists(locContainedArtifactPath)) {
			if (locTestedExtensions.get() == null) {
				locTestedExtensions.set(new ArrayList<>());
				locTestedExtensions.get().add(aPreferredConfigFileExtension);
			}
			String locNextExtensionToTest = null;
			for (AInConfigurationFileKind locSource : AInConfigurationFileKind.values()) {
				for (String locSourceExtension : locSource.getConfigurationFileExtensionPossibilities()	) {
					if (locTestedExtensions.get().contains(locSourceExtension))
						continue;
					locNextExtensionToTest = locSourceExtension;
					locTestedExtensions.get().add(locNextExtensionToTest);
					break;
				}
			}
			if (locNextExtensionToTest == null) {
				locContainedArtifactPath = null;
			}
			else {
				locContainedArtifactPath = aConfigFileFolder.resolve(
						ARTIFACT_CONFIG_FILE_NAME_WITHOUT_EXT + "." + locNextExtensionToTest);
			}
			aPathChecker.accept(locContainedArtifactPath);
		}
		return locContainedArtifactPath;
	}
}
