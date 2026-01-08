package eu.algites.tool.build.model.artifact.impl;

import eu.algites.tool.build.model.artifact.common.AIiArtifactDependency;
import eu.algites.tool.build.model.artifact.common.AIiControlledPolicyArtifact;
import eu.algites.tool.build.model.artifact.common.AIiControlledProductInterfaceBomArtifact;
import eu.algites.tool.build.model.artifact.common.AIiControlledProductVariantBomArtifact;
import eu.algites.tool.build.model.artifact.common.AIiUncontrolledArtifact;

import java.util.List;
import java.util.Objects;

public class AIcControlledProductVariantBomArtifact extends AIcControlledArtifact
		implements AIiControlledProductVariantBomArtifact {

	private AIiControlledPolicyArtifact parent;
	private AIiControlledProductInterfaceBomArtifact productInterfaceBom;
	private List<AIiArtifactDependency<? extends AIiUncontrolledArtifact>> managedProductVariantDependencies;

	@Override
	public AIiControlledPolicyArtifact getParent() {
		return parent;
	}

	public void setParent(AIiControlledPolicyArtifact parent) {
		this.parent = parent;
	}

	@Override
	public AIiControlledProductInterfaceBomArtifact getProductInterfaceBom() {
		return productInterfaceBom;
	}

	public void setProductInterfaceBom(AIiControlledProductInterfaceBomArtifact productInterfaceBom) {
		this.productInterfaceBom = productInterfaceBom;
	}

	@Override
	public List<AIiArtifactDependency<? extends AIiUncontrolledArtifact>> getManagedProductVariantDependencies() {
		return managedProductVariantDependencies;
	}

	public void setManagedProductVariantDependencies(
			List<AIiArtifactDependency<? extends AIiUncontrolledArtifact>> managedProductVariantDependencies) {
		this.managedProductVariantDependencies = managedProductVariantDependencies;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + Objects.hashCode(parent);
		result = 31 * result + Objects.hashCode(productInterfaceBom);
		result = 31 * result + Objects.hashCode(managedProductVariantDependencies);
		return result;
	}

	@Override
	public boolean equals(final Object aO) {
		if (!(aO instanceof AIcControlledProductVariantBomArtifact locthat))
			return false;
		if (!super.equals(aO))
			return false;

		return Objects.equals(parent, locthat.parent) && Objects.equals(productInterfaceBom, locthat.productInterfaceBom)
				&& Objects.equals(managedProductVariantDependencies, locthat.managedProductVariantDependencies);
	}

	@Override
	public String toString() {
		return "AIcControlledProductVariantBomArtifact{" + super.toString() + "\n" +
				"parent=" + parent +
				", productInterfaceBom=" + productInterfaceBom +
				", managedProductVariantDependencies=" + managedProductVariantDependencies +
				'}';
	}
}
