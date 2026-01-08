package eu.algites.tool.build.model.yaml.dto;

import eu.algites.tool.build.model.common.AInArtifactKind;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Single "union" DTO for all artifact kinds. Unused fields for a given kind may be omitted in YAML.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AIcYamlArtifactDefDTO {

	@JsonProperty(value = "id", required = true)
	private String id;

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

	/**
	 * for controlled artifacts
	 */
	@JsonProperty("versionContext")
	private AIcYamlVersionContextDefDTO versionContext;

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
	 * Aggregator only
	 */
	@JsonProperty("containedArtifacts")
	private List<String> containedArtifactIds;

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
	private List<AIcYamlDependencyDefDTO> directDependencies;

	/**
	 * managed deps for POLICY (background) and PB-BOM
	 */
	@JsonProperty("managedPolicyBackgroundDependencies")
	private List<AIcYamlDependencyDefDTO> managedPolicyBackgroundDependencies;

	/**
	 * managed deps for PI-BOM
	 */
	@JsonProperty("managedInterfaceDependencies")
	private List<AIcYamlDependencyDefDTO> managedInterfaceDependencies;

	/**
	 * managed deps for PV-BOM
	 */
	@JsonProperty("managedProductVariantDependencies")
	private List<AIcYamlDependencyDefDTO> managedProductVariantDependencies;

	// getters/setters

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public AIcYamlVersionContextDefDTO getVersionContext() {
		return versionContext;
	}

	public void setVersionContext(AIcYamlVersionContextDefDTO aVersionContext) {
		this.versionContext = aVersionContext;
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

	public List<String> getContainedArtifactIds() {
		return containedArtifactIds;
	}

	public void setContainedArtifactIds(List<String> containedArtifactIds) {
		this.containedArtifactIds = containedArtifactIds;
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

	public List<AIcYamlDependencyDefDTO> getDirectDependencies() {
		return directDependencies;
	}

	public void setDirectDependencies(List<AIcYamlDependencyDefDTO> directDependencies) {
		this.directDependencies = directDependencies;
	}

	public List<AIcYamlDependencyDefDTO> getManagedPolicyBackgroundDependencies() {
		return managedPolicyBackgroundDependencies;
	}

	public void setManagedPolicyBackgroundDependencies(List<AIcYamlDependencyDefDTO> managedPolicyBackgroundDependencies) {
		this.managedPolicyBackgroundDependencies = managedPolicyBackgroundDependencies;
	}

	public List<AIcYamlDependencyDefDTO> getManagedInterfaceDependencies() {
		return managedInterfaceDependencies;
	}

	public void setManagedInterfaceDependencies(List<AIcYamlDependencyDefDTO> managedInterfaceDependencies) {
		this.managedInterfaceDependencies = managedInterfaceDependencies;
	}

	public List<AIcYamlDependencyDefDTO> getManagedProductVariantDependencies() {
		return managedProductVariantDependencies;
	}

	public void setManagedProductVariantDependencies(List<AIcYamlDependencyDefDTO> managedProductVariantDependencies) {
		this.managedProductVariantDependencies = managedProductVariantDependencies;
	}
}
