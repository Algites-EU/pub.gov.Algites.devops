package eu.algites.tool.build.model.repository.impl;

import eu.algites.tool.build.model.artifact.intf.AIiAbstractControlledArtifact;
import eu.algites.tool.build.model.repository.intf.AIiSourceRepository;
import eu.algites.tool.build.model.version.intf.AIiVersionContext;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class AIcSourceRepository implements AIiSourceRepository {

	private String id;
	private String name;
	private String description;
	private AIiVersionContext versionContext;
	private Path repositoryConfigurationFile;
	private Map<Path, AIiAbstractControlledArtifact> containedArtifacts = new LinkedHashMap<>();

	public AIcSourceRepository() {
	}

	@Override
	public String getId() {
		return id;
	}

	/**
	 * @param aId the id
	 */
	public void setId(final String aId) {
		id = aId;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * @param aName the name
	 */
	public void setName(final String aName) {
		name = aName;
	}

	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * @param aDescription the description
	 */
	public void setDescription(final String aDescription) {
		description = aDescription;
	}

	@Override
	public AIiVersionContext getVersionContext() {
		return versionContext;
	}

	public void setVersionContext(AIiVersionContext versionContext) {
		this.versionContext = versionContext;
	}

	@Override
	public Path getRepositoryConfigurationFile() {
		return repositoryConfigurationFile;
	}

	/**
	 * @param aRepositoryConfigurationFile the repositoryConfigurationFile to set
	 */
	public void setRepositoryConfigurationFile(final Path aRepositoryConfigurationFile) {
		repositoryConfigurationFile = aRepositoryConfigurationFile;
	}

	@Override
	public Map<Path, AIiAbstractControlledArtifact> getContainedArtifacts() {
		return containedArtifacts;
	}

	/**
	 * @param aContainedArtifacts the containedArtifacts
	 */
	public void setContainedArtifacts(final Map<Path, AIiAbstractControlledArtifact> aContainedArtifacts) {
		containedArtifacts = aContainedArtifacts;
	}

	@Override
	public final boolean equals(final Object aO) {
		if (!(aO instanceof AIcSourceRepository locthat))
			return false;

		return Objects.equals(id, locthat.id) && Objects.equals(name, locthat.name) && Objects.equals(
				description,
				locthat.description) && Objects.equals(versionContext, locthat.versionContext) && Objects.equals(
				repositoryConfigurationFile,
				locthat.repositoryConfigurationFile) && Objects.equals(containedArtifacts, locthat.containedArtifacts);
	}

	@Override
	public int hashCode() {
		int result = Objects.hashCode(id);
		result = 31 * result + Objects.hashCode(name);
		result = 31 * result + Objects.hashCode(description);
		result = 31 * result + Objects.hashCode(versionContext);
		result = 31 * result + Objects.hashCode(repositoryConfigurationFile);
		result = 31 * result + Objects.hashCode(containedArtifacts);
		return result;
	}

	@Override
	public String toString() {
		return "AIcSourceRepository{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", versionContext=" + versionContext +
				", repositoryConfigurationFile=" + repositoryConfigurationFile +
				", containedArtifacts=" + containedArtifacts +
				'}';
	}
}
