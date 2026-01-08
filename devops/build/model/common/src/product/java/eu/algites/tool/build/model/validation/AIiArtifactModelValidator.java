package eu.algites.tool.build.model.validation;

import eu.algites.tool.build.model.common.AIiArtifactDependency;
import eu.algites.tool.build.model.common.AIiArtifactDependencyScope;
import eu.algites.tool.build.model.common.AIiArtifactLink;
import eu.algites.tool.build.model.common.AIiArtifactProjection;
import eu.algites.tool.build.model.common.AIiDependencyScopeBehavior;
import eu.algites.tool.build.model.common.AIiDirectDependencies;
import eu.algites.tool.build.model.common.AIiManagedDependencies;
import eu.algites.tool.build.model.common.AInArtifactDependencyScopeLevel;

/**
 * <p>
 * Title: {@link AIiArtifactModelValidator}
 * </p>
 * <p>
 * Description: TODO: Add description
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 08.01.26 14:27
 *
 * [[TO-BE-VERIFIED]]
 *
 * <h1>Algites Artifact Dependency Validator Rules (Spec)</h1>
 *
 * <p>
 * This document describes validation rules for dependencies declared in Algites artifact models.
 * It is intended to be copied into the validator implementation as a "living spec" and to be
 * kept in sync with the model.
 * </p>
 *
 * <h2>Terminology</h2>
 *
 * <ul>
 *   <li><b>Owner</b>: The artifact on which a dependency is declared.</li>
 *   <li><b>Target</b>: The artifact referenced by the dependency (via {@link AIiArtifactLink#getArtifact()}).</li>
 *   <li><b>Context</b>:
 *     <ul>
 *       <li><b>DIRECT</b>: dependency appears in {@link AIiDirectDependencies#getDirectDependencies()} (runtime dependencies list).</li>
 *       <li><b>MANAGED</b>: dependency appears in {@link AIiManagedDependencies#getManagedDependencies()} (dependencyManagement-style list).</li>
 *     </ul>
 *   </li>
 *   <li><b>ScopeLevel</b>: Logical dependency scope level (e.g. COMPILE/RUNTIME/TEST/PROVIDED/IMPORT, optionally UNDEFINED).</li>
 *   <li><b>Projection</b>: Output selection for the target (type/classifier), i.e. {@link AIiArtifactProjection#getOutputTypeId()}/{@link AIiArtifactProjection#getOutputClassifier()}.</li>
 * </ul>
 *
 * <h2>Severity</h2>
 * <ul>
 *   <li><b>ERROR</b>: Build must fail; output POM/Gradle generation would be invalid or ambiguous.</li>
 *   <li><b>WARN</b>: Unusual or discouraged modeling; still resolvable and generatable.</li>
 * </ul>
 *
 * <h2>General Rules (always enforced)</h2>
 *
 * <ul>
 *   <li><b>G-01 Target existence (ERROR)</b><br/>
 *     Every dependency must reference exactly one target artifact:
 *     {@link AIiArtifactLink#getLinkedArtifact()} must not be {@code null}.</li>
 *
 *   <li><b>G-02 Scope presence (ERROR)</b><br/>
 *     {@link AIiArtifactDependency#getDependencyScope()} must not be {@code null}.<br/>
 *     If {@code UNDEFINED} scope level exists, see UNDEFINED rules below.</li>
 *
 *   <li><b>G-03 Scope allowed for owner kind (ERROR)</b><br/>
 *     The dependency scope level must be allowed for the <i>owner</i> artifact kind per
 *     {@link AInArtifactDependencyScopeLevel#getAllowedForUsageInDependencyWithArtifactKinds()}.<br/>
 *     (Interpretation: "where a scope may be used" is a property of the usage site / owner.)</li>
 *
 *   <li><b>G-04 Projection consistent with target kind (ERROR)</b><br/>
 *     If the target kind requires a POM projection, then
 *     {@link AIiArtifactProjection#getOutputTypeId()} must be {@code "pom"}.</li>
 *
 *   <li><b>G-05 Output type whitelist (ERROR)</b><br/>
 *     {@link AIiArtifactProjection#getOutputTypeId()} must be within the allowed Maven types
 *     (at minimum: {@code jar}, {@code pom}). Reject unknown types unless explicitly supported.</li>
 * </ul>
 *
 * <h2>IMPORT Scope Rules</h2>
 *
 * <ul>
 *   <li><b>I-01 IMPORT is managed-only (ERROR)</b><br/>
 *     If {@link AInArtifactDependencyScopeLevel#IMPORT} is used, the dependency must be declared in MANAGED context only
 *     (dependencyManagement-style). It must not appear in DIRECT dependencies.<br/>
 *     This is aligned with Maven BOM-import semantics.<br/>
 *     (Model hint: {@link AInArtifactDependencyScopeLevel#isUsedForManagedDependenciesOnly()}.)</li>
 *
 *   <li><b>I-02 IMPORT requires POM projection (ERROR)</b><br/>
 *     If {@link AInArtifactDependencyScopeLevel#IMPORT} is used, then {@link AIiArtifactProjection#getOutputTypeId()} must be {@code "pom"}.</li>
 *
 *   <li><b>I-03 IMPORT requires an importable target (ERROR)</b><br/>
 *     If {@link AInArtifactDependencyScopeLevel#IMPORT} is used, then the target kind must be an importable BOM kind,
 *     e.g. {@code POLICY_BACKGROUND_BOM}, {@code PRODUCT_INTERFACE_BOM}, {@code PRODUCT_VARIANT_BOM},
 *     or {@code UNCONTROLLED_BOM}.</li>
 *
 *   <li><b>I-04 IMPORT should not use classifier (WARN)</b><br/>
 *     If {@link AInArtifactDependencyScopeLevel#IMPORT} is used, then {@link AIiArtifactProjection#getOutputClassifier()} should be empty/null.
 *     (Classifier-based BOM imports are typically discouraged.)</li>
 * </ul>
 *
 * <h2>Context Rules: DIRECT vs MANAGED</h2>
 *
 * <ul>
 *   <li><b>D-01 AGGREGATOR has no dependencies (ERROR)</b><br/>
 *     If owner.kind is {@code AGGREGATOR}, both DIRECT and MANAGED dependencies must be empty.</li>
 *
 *   <li><b>D-02 BOM artifacts have no DIRECT dependencies (ERROR)</b><br/>
 *     If owner.kind is any BOM kind ({@code POLICY_BACKGROUND_BOM}, {@code PRODUCT_INTERFACE_BOM}, {@code PRODUCT_VARIANT_BOM}),
 *     then DIRECT dependencies must be empty.</li>
 *
 *   <li><b>D-03 MANAGED dependencies allowed only on POLICY and BOM kinds (ERROR)</b><br/>
 *     MANAGED context dependencies are only valid for owners of kind {@code POLICY} or any BOM kind.
 *     Any other owner.kind declaring MANAGED dependencies is invalid.</li>
 *
 *   <li><b>D-04 PRODUCT_CORE/TEST_CORE have DIRECT only (ERROR)</b><br/>
 *     If owner.kind is {@code PRODUCT_CORE} or {@code TEST_CORE}, then MANAGED dependencies must be empty
 *     (unless the model explicitly supports managed entries on products in the future).</li>
 * </ul>
 *
 * <h2>Target Kind vs Scope Rules (practical compatibility)</h2>
 *
 * <ul>
 *   <li><b>S-01 UNCONTROLLED_BOM should primarily be used for IMPORT (WARN/ERROR)</b><br/>
 *     If {@code target.kind == UNCONTROLLED_BOM} and {@link AIiArtifactDependencyScope#getLevel()} is not {@link AInArtifactDependencyScopeLevel#IMPORT}, then:
 *     <ul>
 *       <li>Prefer WARN if you want to allow exceptional cases.</li>
 *       <li>Prefer ERROR if {@code UNCONTROLLED_BOM} is intended strictly as "importable POM only".</li>
 *     </ul>
 *   </li>
 *
 *   <li><b>S-02 IMPORT must not target UNCONTROLLED_CORE (ERROR)</b><br/>
 *     If {@link AIiArtifactDependencyScope#getLevel()} is {@link AInArtifactDependencyScopeLevel#IMPORT}, then {@code target.kind != UNCONTROLLED_CORE}.</li>
 *
 *   <li><b>S-03 Direct dependency on POLICY is unusual (WARN)</b><br/>
 *     If context is DIRECT and {@code target.kind == POLICY}, emit WARN:
 *     "Depending on a POLICY artifact as a runtime dependency is unusual; consider importing a BOM or using it as a parent policy."</li>
 *
 *   <li><b>S-04 Dependency on AGGREGATOR is invalid (ERROR)</b><br/>
 *     If {@code target.kind == AGGREGATOR}, then ERROR. Aggregators are not consumable artifacts.</li>
 * </ul>
 *
 * <h2>Scope Behavior Rules</h2>
 *
 * <ul>
 *   <li><b>B-01 Behavior presence (ERROR)</b><br/>
 *     {@link AIiArtifactDependencyScope#getBehavior()} must not be {@code null} (if behavior is mandatory in the model).</li>
 *
 *   <li><b>B-02 Managed locked defaults (ERROR)</b><br/>
 *     If a MANAGED entry has {@link AIiDependencyScopeBehavior#isLocked()} {@code == true}, then any DIRECT usage that matches the same coordinates
 *     must not override the managed default scope level (i.e., effective scope must equal managed scope).<br/>
 *     (This rule requires cross-artifact validation during resolution.)</li>
 *
 *   <li><b>B-03 Transitivity on MANAGED entries has no Maven effect (WARN)</b><br/>
 *     If context is MANAGED and {@link AIiDependencyScopeBehavior#isTransitive()} {@code == true}, emit WARN (or ignore):
 *     "Transitivity on managed entries has no Maven effect; it is only meaningful for Algites resolver logic."</li>
 * </ul>
 *
 * <h2>UNDEFINED Scope Rules (only if the model introduces an explicit UNDEFINED level)</h2>
 *
 * <ul>
 *   <li><b>U-01 UNDEFINED is managed-only (ERROR)</b><br/>
 *     If {@link AIiArtifactDependencyScope#getLevel()} is {@link AInArtifactDependencyScopeLevel#UNDEFINED}, dependency must be declared only in MANAGED context.
 *     Direct dependencies must always be explicit (non-undefined) for valid POM generation.</li>
 *
 *   <li><b>U-02 UNDEFINED must be resolved before materialization (ERROR)</b><br/>
 *     Before generating final POM {@code &lt;dependencies&gt;}, every effective dependency must have a concrete scope
 *     (i.e., not UNDEFINED). If any effective dependency remains UNDEFINED, fail with ERROR.</li>
 *
 *   <li><b>U-03 Override resolution (INFO)</b><br/>
 *     If a MANAGED entry has UNDEFINED scope and a DIRECT usage exists, the DIRECT scope is authoritative
 *     (consistent with Maven dependencyManagement defaults).</li>
 * </ul>
 *
 * <h2>Version/Coordinates Rules (generation feasibility)</h2>
 *
 * <ul>
 *   <li><b>V-01 External versions must be resolvable (ERROR)</b><br/>
 *     For {@code target.kind == UNCONTROLLED_CORE} used in DIRECT dependencies, the version must be resolvable:
 *     either provided explicitly on the uncontrolled artifact, or supplied via MANAGED policy/bom entry.
 *     If neither exists, generation of a valid POM is impossible.</li>
 *
 *   <li><b>V-02 Controlled artifacts do not carry explicit versions (ERROR)</b><br/>
 *     Controlled artifacts derive versions from the ProductVersionContext; any explicit version fields
 *     (if present in the model) must be absent/empty.</li>
 * </ul>
 *
 * <h2>Note on "allowedArtifactKinds": Owner vs Target</h2>
 *
 * <p>
 * It is strongly recommended to interpret {@link AInArtifactDependencyScopeLevel#getAllowedForUsageInDependencyWithArtifactKinds()} on scope levels as constraints on the
 * <b>owner kind</b> ("where this scope can be used"), not on the target kind. Target-kind constraints should be
 * expressed by dedicated rules (e.g., IMPORT importable target kinds, POM-required kinds, etc.).
 * This keeps the validator logic readable and avoids mixing unrelated semantics.
 * </p>
 * [[/TO-BE-VERIFIED]]
 */
public interface AIiArtifactModelValidator {
}
