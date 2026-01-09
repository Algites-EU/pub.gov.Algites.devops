package eu.algites.tool.build.model.common.dto;

import eu.algites.tool.build.model.artifact.dto.AIcArtifactDefDTO;
import eu.algites.tool.build.model.repository.dto.AIcSourceRepositoryDefDTO;
import eu.algites.tool.build.model.version.dto.AIcVersionContextDefDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AIcCommonHolderDefDTO {

	/**
	 * for controlled artifacts
	 */
	@JsonProperty("versionContext")
	private AIcVersionContextDefDTO versionContextDefDTO;

	@JsonProperty("artifact")
	private AIcArtifactDefDTO artifactDefDTO = null;

	@JsonProperty("sourceRepository")
	private AIcSourceRepositoryDefDTO sourceRepositoryDefDTO = null;

	public AIcVersionContextDefDTO getVersionContextDefDTO() {
		return versionContextDefDTO;
	}

	public void setVersionContext(AIcVersionContextDefDTO aVersionContextDefDTO) {
		this.versionContextDefDTO = aVersionContextDefDTO;
	}

	/**
	 * @return the sourceRepositoryDefDTO
	 */
	public AIcSourceRepositoryDefDTO getSourceRepositoryDefDTO() {
		return sourceRepositoryDefDTO;
	}

	/**
	 * @param aSourceRepositoryDefDTO the sourceRepositoryDefDTO
	 */
	public void setSourceRepositoryDefDTO(final AIcSourceRepositoryDefDTO aSourceRepositoryDefDTO) {
		sourceRepositoryDefDTO = aSourceRepositoryDefDTO;
	}

	public AIcArtifactDefDTO getArtifactDefDTO() {
		return artifactDefDTO;
	}

	public void setArtifactDefDTO(AIcArtifactDefDTO aArtifactDefDTO) {
		this.artifactDefDTO = aArtifactDefDTO;
	}

}
