package eu.algites.tool.build.model.artifact.yaml;

import eu.algites.tool.build.model.artifact.common.AIiAbstractArtifact;
import eu.algites.tool.build.model.artifact.common.AIiArtifactDependency;
import eu.algites.tool.build.model.artifact.common.AIiControlledPolicyArtifact;
import eu.algites.tool.build.model.artifact.common.AIiArtifactProjection;
import eu.algites.tool.build.model.artifact.common.AIiControlledArtifact;
import eu.algites.tool.build.model.artifact.common.AInArtifactKind;
import eu.algites.tool.build.model.artifact.impl.AIcArtifactDependencyLink;
import eu.algites.tool.build.model.artifact.impl.AIcArtifactDependencyScope;
import eu.algites.tool.build.model.artifact.impl.AIcArtifactProjection;
import eu.algites.tool.build.model.artifact.impl.AIcControlledAggregatorArtifact;
import eu.algites.tool.build.model.artifact.impl.AIcControlledArtifact;
import eu.algites.tool.build.model.artifact.impl.AIcControlledPolicyArtifact;
import eu.algites.tool.build.model.artifact.impl.AIcControlledPolicyBackgroundBomArtifact;
import eu.algites.tool.build.model.artifact.impl.AIcControlledProductCoreArtifact;
import eu.algites.tool.build.model.artifact.impl.AIcControlledProductInterfaceBomArtifact;
import eu.algites.tool.build.model.artifact.impl.AIcControlledProductVariantBomArtifact;
import eu.algites.tool.build.model.artifact.impl.AIcDependencyScopeBehavior;
import eu.algites.tool.build.model.artifact.impl.AIcReleaseLine;
import eu.algites.tool.build.model.artifact.impl.AIcReleaseLineRevision;
import eu.algites.tool.build.model.artifact.impl.AIcUncontrolledArtifact;
import eu.algites.tool.build.model.artifact.impl.AIcUncontrolledBomArtifact;
import eu.algites.tool.build.model.artifact.impl.AIcUncontrolledCoreArtifact;
import eu.algites.tool.build.model.artifact.impl.AIcVersionContext;
import eu.algites.tool.build.model.artifact.impl.AIcVersionQualifier;
import eu.algites.tool.build.model.artifact.yaml.dto.AIcYamlArtifactDefDTO;
import eu.algites.tool.build.model.artifact.yaml.dto.AIcYamlArtifactModelDefDTO;
import eu.algites.tool.build.model.artifact.yaml.dto.AIcYamlDependencyDefDTO;
import eu.algites.tool.build.model.artifact.yaml.dto.AIcYamlExclusionDefDTO;
import eu.algites.tool.build.model.artifact.yaml.dto.AIcYamlScopeDefDTO;
import eu.algites.tool.build.model.artifact.yaml.dto.AIcYamlVersionContextDefDTO;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.dataformat.yaml.YAMLFactory;
import tools.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Loads the artifact model from YAML into a resolved in-memory graph.
 *
 * <p>ementation note: This loader intentionally keeps "model interfaces" clean of Jackson annotations.
 * YAML is deserialized into DTOs and then converted into the {@code impl} POJOs.</p>
 * @author linhart1
 */
public final class AIcArtifactModelYamlLoader {

    private AIcArtifactModelYamlLoader() {}

    public static AIcArtifactModel load(Path yamlPath) throws IOException {
        try (InputStream in = Files.newInputStream(yamlPath)) {
            return load(in);
        }
    }

    public static AIcArtifactModel load(InputStream yamlStream) throws IOException {
        // Jackson 3: mappers are immutable; use the builder to configure features
        YAMLMapper mapper = YAMLMapper.builder(new YAMLFactory())
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .build();

        AIcYamlArtifactModelDefDTO def = mapper.readValue(yamlStream, AIcYamlArtifactModelDefDTO.class);
        return resolve(def);
    }

    private static AIcArtifactModel resolve(AIcYamlArtifactModelDefDTO def) {
        if (def == null || def.getArtifacts() == null) {
            return new AIcArtifactModel(List.of(), Map.of());
        }

        // 1) Instantiate artifacts (without links)
        Map<String, AIiAbstractArtifact> byId = new LinkedHashMap<>();
        List<AIiAbstractArtifact> ordered = new ArrayList<>();

        for (AIcYamlArtifactDefDTO a : def.getArtifacts()) {
            require(a.getId(), "artifact.id");
            require(a.getKind(), "artifact.kind");
            require(a.getGroupId(), "artifact.groupId");
            require(a.getArtifactIdBase(), "artifact.artifactIdBase");

            if (byId.containsKey(a.getId())) {
                throw new IllegalArgumentException("Duplicate artifact id: " + a.getId());
            }

            AIiAbstractArtifact created = createArtifactShell(a);
            byId.put(a.getId(), created);
            ordered.add(created);
        }

        // 2) Resolve cross-references and dependencies
        for (AIcYamlArtifactDefDTO a : def.getArtifacts()) {
            AIiAbstractArtifact owner = byId.get(a.getId());

            // parent
            if (a.getParentId() != null) {
                AIiAbstractArtifact parent = requireRef(byId, a.getParentId(), "parent of " + a.getId());
                setParent(owner, parent);
            }

            // artifact-kind specific cross-references
            if (owner instanceof AIcControlledProductInterfaceBomArtifact) {
                if (a.getPolicyBackgroundBomId() != null) {
                    AIiAbstractArtifact pb = requireRef(byId, a.getPolicyBackgroundBomId(), "policyBackgroundBom of " + a.getId());
                    ((AIcControlledProductInterfaceBomArtifact) owner).setPolicyBackgroundBom((AIcControlledPolicyBackgroundBomArtifact) pb);
                }
            }

            if (owner instanceof AIcControlledProductVariantBomArtifact) {
                if (a.getProductInterfaceBomId() != null) {
                    AIiAbstractArtifact pib = requireRef(byId, a.getProductInterfaceBomId(), "productInterfaceBom of " + a.getId());
                    ((AIcControlledProductVariantBomArtifact) owner).setProductInterfaceBom((AIcControlledProductInterfaceBomArtifact) pib);
                }
            }

            if (owner instanceof AIcControlledAggregatorArtifact) {
                if (a.getContainedArtifactIds() != null) {
                    List<AIiControlledArtifact> contained = new ArrayList<>();
                    for (String cid : a.getContainedArtifactIds()) {
                        AIiAbstractArtifact ca = requireRef(byId, cid, "containedArtifacts of " + a.getId());
                        if (!(ca instanceof AIiControlledArtifact)) {
                            throw new IllegalArgumentException("Aggregator '" + a.getId() + "' contains non-controlled artifact '" + cid + "'");
                        }
                        contained.add((AIiControlledArtifact) ca);
                    }
                    ((AIcControlledAggregatorArtifact) owner).setContainedArtifacts(contained);
                }
            }

            // dependencies
            setDeps(owner, a, byId);
        }

        return new AIcArtifactModel(ordered, byId);
    }

    private static void setDeps(AIiAbstractArtifact owner, AIcYamlArtifactDefDTO a, Map<String, AIiAbstractArtifact> byId) {
        if (owner instanceof AIcControlledPolicyArtifact) {
            ((AIcControlledPolicyArtifact) owner).setDirectDependencies(resolveDeps(a.getDirectDependencies(), byId));
            ((AIcControlledPolicyArtifact) owner).setManagedPolicyBackgroundDependencies(resolveDeps(a.getManagedPolicyBackgroundDependencies(), byId));
        }

        if (owner instanceof AIcControlledProductCoreArtifact) {
            ((AIcControlledProductCoreArtifact) owner).setDirectDependencies(resolveDeps(a.getDirectDependencies(), byId));
        }

        if (owner instanceof AIcControlledPolicyBackgroundBomArtifact) {
            ((AIcControlledPolicyBackgroundBomArtifact) owner).setManagedPolicyBackgroundDependencies(resolveDeps(a.getManagedPolicyBackgroundDependencies(), byId));
        }

        if (owner instanceof AIcControlledProductInterfaceBomArtifact) {
            ((AIcControlledProductInterfaceBomArtifact) owner).setManagedInterfaceDependencies(resolveDeps(a.getManagedInterfaceDependencies(), byId));
        }

        if (owner instanceof AIcControlledProductVariantBomArtifact) {
            ((AIcControlledProductVariantBomArtifact) owner).setManagedProductVariantDependencies(resolveDeps(a.getManagedProductVariantDependencies(), byId));
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends AIiAbstractArtifact> List<AIiArtifactDependency<? extends T>> resolveDeps(
            List<AIcYamlDependencyDefDTO> deps, Map<String, AIiAbstractArtifact> byId) {

        if (deps == null) return null;

        List<AIiArtifactDependency<? extends T>> out = new ArrayList<>();
        for (AIcYamlDependencyDefDTO d : deps) {
            AIiAbstractArtifact target = requireRef(byId, d.getTargetId(), "dependency.target");
            AIcArtifactDependencyLink<T> dep = new AIcArtifactDependencyLink<>();
            dep.setLinkedArtifact((T) target);
            dep.setDependencyScope(resolveScope(d.getScope()));

            if (d.getExclusions() != null) {
                for (AIcYamlExclusionDefDTO ex : d.getExclusions()) {
                    AIiAbstractArtifact exTarget = requireRef(byId, ex.getTargetId(), "dependency.exclusions.target");
									  AIcArtifactProjection<AIiAbstractArtifact> proj = new AIcArtifactProjection<>();
                    proj.setLinkedArtifact(exTarget);
                    proj.setOutputClassifier(ex.getOutputClassifier());
                    proj.setOutputTypeId(ex.getOutputTypeId());
                    dep.addDependencyExclusion((AIiArtifactProjection<? extends AIiAbstractArtifact>) proj);
                }
            }

            out.add(dep);
        }
        return out;
    }

    private static AIcArtifactDependencyScope resolveScope(AIcYamlScopeDefDTO scope) {
        if (scope == null) return null;

        AIcDependencyScopeBehavior behavior = new AIcDependencyScopeBehavior(
                Boolean.TRUE.equals(scope.getLocked()),
                Boolean.TRUE.equals(scope.getTransitive())
        );

        return new AIcArtifactDependencyScope(scope.getLevel(), behavior);
    }

    private static AIiAbstractArtifact createArtifactShell(AIcYamlArtifactDefDTO a) {
        AInArtifactKind kind = a.getKind();

        // common
        if (kind == AInArtifactKind.UNCONTROLLED_CORE || kind == AInArtifactKind.UNCONTROLLED_BOM) {
            require(a.getVersion(), "artifact.version (uncontrolled)");
            AIcUncontrolledArtifact u = kind == AInArtifactKind.UNCONTROLLED_CORE ?
								new AIcUncontrolledCoreArtifact() : new AIcUncontrolledBomArtifact();
            u.setArtifactGroupId(a.getGroupId());
            u.setArtifactIdBase(a.getArtifactIdBase());
            u.setArtifactKind(kind);
            u.setArtifactVersion(a.getVersion());
            return u;
        }

        // controlled kinds
        AIcVersionContext vc = resolveVersionContext(a.getVersionContext(), a.getId());
        AIcControlledArtifact base = new AIcControlledArtifact();
        base.setArtifactGroupId(a.getGroupId());
        base.setArtifactIdBase(a.getArtifactIdBase());
        base.setArtifactKind(kind);
        base.setVersionContext(vc);

        switch (kind) {
            case POLICY: {
                AIcControlledPolicyArtifact p = new AIcControlledPolicyArtifact();
                copyControlledBase(base, p);
                p.setPolicyDefinitionUid(a.getPolicyDefinitionUid());
                p.setPolicyDefinitionVersion(a.getPolicyDefinitionVersion());
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
                // fallback: still load it as a "plain controlled artifact" with kind set.
                return base;
        }
    }

    private static void copyControlledBase(AIcControlledArtifact src, AIcControlledArtifact dst) {
        dst.setArtifactGroupId(src.getArtifactGroupId());
        dst.setArtifactIdBase(src.getArtifactIdBase());
        dst.setArtifactKind(src.getArtifactKind());
        dst.setVersionContext(src.getVersionContext());
    }

    private static AIcVersionContext resolveVersionContext(AIcYamlVersionContextDefDTO vc, String artifactId) {
        if (vc == null) {
            // some kinds may not require a version context in early stages
            return null;
        }
        Objects.requireNonNull(vc.getReleaseLine(), "versionContext.releaseLine for " + artifactId);
        Objects.requireNonNull(vc.getQualifierKind(), "versionContext.qualifierKind for " + artifactId);
        Objects.requireNonNull(vc.getQualifierLabel(), "versionContext.qualifierLabel for " + artifactId);

        return new AIcVersionContext(
                new AIcReleaseLine(vc.getReleaseLine()),
                new AIcReleaseLineRevision(vc.getRevision()),
                new AIcVersionQualifier(vc.getQualifierKind(), vc.getQualifierLabel())
        );
    }

    private static void setParent(AIiAbstractArtifact owner, AIiAbstractArtifact parent) {
        if (owner instanceof AIcControlledPolicyArtifact) {
            ((AIcControlledPolicyArtifact) owner).setParent(parent);
            return;
        }
        if (owner instanceof AIcControlledPolicyBackgroundBomArtifact) {
            ((AIcControlledPolicyBackgroundBomArtifact) owner).setParent((AIiControlledPolicyArtifact) parent);
            return;
        }
        if (owner instanceof AIcControlledProductCoreArtifact) {
            ((AIcControlledProductCoreArtifact) owner).setParent((AIiControlledPolicyArtifact) parent);
            return;
        }
        if (owner instanceof AIcControlledProductInterfaceBomArtifact) {
            ((AIcControlledProductInterfaceBomArtifact) owner).setParent((AIiControlledPolicyArtifact) parent);
            return;
        }
        if (owner instanceof AIcControlledProductVariantBomArtifact) {
            ((AIcControlledProductVariantBomArtifact) owner).setParent((AIiControlledPolicyArtifact) parent);
            return;
        }
        // for plain controlled/uncontrolled artifacts we currently ignore parent
    }

    private static AIiAbstractArtifact requireRef(Map<String, AIiAbstractArtifact> byId, String id, String what) {
        require(id, what);
        AIiAbstractArtifact a = byId.get(id);
        if (a == null) {
            throw new IllegalArgumentException("Unknown artifact reference '" + id + "' (" + what + ")");
        }
        return a;
    }

    private static void require(Object value, String what) {
        if (value == null) throw new IllegalArgumentException("Missing required value: " + what);
        if (value instanceof String && ((String) value).trim().isEmpty()) {
            throw new IllegalArgumentException("Missing required value: " + what);
        }
    }
}
