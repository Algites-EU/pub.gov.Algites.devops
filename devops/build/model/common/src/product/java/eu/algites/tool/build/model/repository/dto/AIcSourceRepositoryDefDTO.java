package eu.algites.tool.build.model.repository.dto;

import static eu.algites.tool.build.model.utils.AIsArtifactModelUtils.getArtifactCoordinatedId;

import eu.algites.tool.build.model.artifact.dto.AIcDependencyDefDTO;
import eu.algites.tool.build.model.artifact.intf.AInArtifactKind;
import eu.algites.tool.build.model.common.dto.AIcArtifactContainerDefDTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Single "union" DTO for all artifact kinds. Unused fields for a given kind may be omitted in YAML.
 * @author linhart1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AIcSourceRepositoryDefDTO extends AIcArtifactContainerDefDTO {

	@JsonProperty(value = "id", required = true)
	private String repositoryId;

	@JsonProperty(value = "displayName", required = true)
	private String displayName;

	@JsonProperty(value = "decription", required = true)
	private String description;

	/**
	 * @return the repositoryId
	 */
	public String getRepositoryId() {
		return repositoryId;
	}

	/**
	 * @param aRepositoryId the repositoryId
	 */
	public void setRepositoryId(final String aRepositoryId) {
		repositoryId = aRepositoryId;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param aDisplayName the displayName
	 */
	public void setDisplayName(final String aDisplayName) {
		displayName = aDisplayName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param aDescription the description
	 */
	public void setDescription(final String aDescription) {
		description = aDescription;
	}

}
