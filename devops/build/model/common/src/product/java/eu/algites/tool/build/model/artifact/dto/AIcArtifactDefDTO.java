package eu.algites.tool.build.model.artifact.dto;

import static eu.algites.tool.build.model.utils.AIsArtifactModelUtils.getArtifactCoordinatedId;

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
public class AIcArtifactDefDTO extends AIcArtifactContainerDefDTO {

	@JsonProperty(value = "kind", required = true)
	private AInArtifactKind kind;

	@JsonProperty(value = "groupId", required = true)
	private String groupId;

	/**
	 * default artifactId base
	 */
	@JsonProperty(value = "artifactIdBase", required = true)
	private String artifactIdBase;

	/**
	 * for uncontrolled artifacts
	 */
	@JsonProperty("version")
	private String version;

	@JsonProperty(value = "displayName", required = true)
	private String displayName;

	@JsonProperty(value = "decription", required = true)
	private String description;

	/**
	 * optional parent reference (id)
	 */
	@JsonProperty("parent")
	private String parentId;

	/**
	 * POLICY only
	 */
	@JsonProperty("policyDefinitionUid")
	private String policyDefinitionUid;

	/**
	 * POLICY only
	 */
	@JsonProperty("policyDefinitionVersion")
	private String policyDefinitionVersion;

	/**
	 * Product Variant BOM only
	 */
	@JsonProperty("productInterfaceBom")
	private String productInterfaceBomId;

	/**
	 * Product Interface BOM only
	 */
	@JsonProperty("policyBackgroundBom")
	private String policyBackgroundBomId;

	/**
	 * direct dependencies (Policy, ProductCore, etc.)
	 */
	@JsonProperty("directDependencies")
	private List<AIcDependencyDefDTO> directDependencies;

	/**
	 * managed deps for POLICY (background) and PB-BOM
	 */
	@JsonProperty("managedPolicyBackgroundDependencies")
	private List<AIcDependencyDefDTO> managedPolicyBackgroundDependencies;

	/**
	 * managed deps for PI-BOM
	 */
	@JsonProperty("managedInterfaceDependencies")
	private List<AIcDependencyDefDTO> managedInterfaceDependencies;

	/**
	 * managed deps for PV-BOM
	 */
	@JsonProperty("managedProductVariantDependencies")
	private List<AIcDependencyDefDTO> managedProductVariantDependencies;

	// getters/setters

	public String getCoordinatedId() {
		return getArtifactCoordinatedId(getGroupId(), getArtifactIdBase());
	}

	public AInArtifactKind getKind() {
		return kind;
	}

	public void setKind(AInArtifactKind kind) {
		this.kind = kind;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String aGroupId) {
		this.groupId = aGroupId;
	}

	public String getArtifactIdBase() {
		return artifactIdBase;
	}

	public void setArtifactIdBase(String aArtifactIdBase) {
		this.artifactIdBase = aArtifactIdBase;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String aVersion) {
		this.version = aVersion;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String aParentId) {
		this.parentId = aParentId;
	}

	public String getPolicyDefinitionUid() {
		return policyDefinitionUid;
	}

	public void setPolicyDefinitionUid(String aPolicyDefinitionUid) {
		this.policyDefinitionUid = aPolicyDefinitionUid;
	}

	public String getPolicyDefinitionVersion() {
		return policyDefinitionVersion;
	}

	public void setPolicyDefinitionVersion(String policyDefinitionVersion) {
		this.policyDefinitionVersion = policyDefinitionVersion;
	}

	public String getProductInterfaceBomId() {
		return productInterfaceBomId;
	}

	public void setProductInterfaceBomId(String productInterfaceBomId) {
		this.productInterfaceBomId = productInterfaceBomId;
	}

	public String getPolicyBackgroundBomId() {
		return policyBackgroundBomId;
	}

	public void setPolicyBackgroundBomId(String policyBackgroundBomId) {
		this.policyBackgroundBomId = policyBackgroundBomId;
	}

	public List<AIcDependencyDefDTO> getDirectDependencies() {
		return directDependencies;
	}

	public void setDirectDependencies(List<AIcDependencyDefDTO> directDependencies) {
		this.directDependencies = directDependencies;
	}

	public List<AIcDependencyDefDTO> getManagedPolicyBackgroundDependencies() {
		return managedPolicyBackgroundDependencies;
	}

	public void setManagedPolicyBackgroundDependencies(List<AIcDependencyDefDTO> managedPolicyBackgroundDependencies) {
		this.managedPolicyBackgroundDependencies = managedPolicyBackgroundDependencies;
	}

	public List<AIcDependencyDefDTO> getManagedInterfaceDependencies() {
		return managedInterfaceDependencies;
	}

	public void setManagedInterfaceDependencies(List<AIcDependencyDefDTO> managedInterfaceDependencies) {
		this.managedInterfaceDependencies = managedInterfaceDependencies;
	}

	public List<AIcDependencyDefDTO> getManagedProductVariantDependencies() {
		return managedProductVariantDependencies;
	}

	public void setManagedProductVariantDependencies(List<AIcDependencyDefDTO> managedProductVariantDependencies) {
		this.managedProductVariantDependencies = managedProductVariantDependencies;
	}
}
