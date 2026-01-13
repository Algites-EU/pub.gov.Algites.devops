[[FINAL-APPROVED]]
# Algites Repository and Artifact Naming Standard
**Version:** 1.7
**Status:** Normative
**Scope:** All repositories and build artifacts within the Algites ecosystem

## 1. Introduction

---

## 2. Namings

### 2.1. Purpose

This document defines a unified and normative standard for naming:

- Git repositories,
- Maven/Gradle artifacts (JARs),
- and related identifiers

across the Algites platform, tools, libraries, products, and customer solutions.

The goals are:

- **Semantic clarity** – names must express domain and purpose.
- **Consistency** – the same concepts are named the same way everywhere.
- **Uniqueness** – artifacts must be identifiable without collisions.
- **Scalability** – the standard must support long-term growth.
- **Governance visibility** – public vs private scope must be immediately visible where needed.

---

### 2.2. Terminology

- **Visibility** – governance scope of a repository or artifact (`pub` or `priv`).
- **Role** – technical role of a repository or artifact (`pltf`, `app`, `lib`, `tool`, `frmw`, etc.).
- **BusinessName** – PascalCase domain or product/application concept (e.g., `Modustro`, `MyGreatProduct`).
- **RepoSubname** – optional lowercase technical qualifier of a repository (e.g., `common`, `core`, `backend`).
- **Module Path** – dot-separated identifier of a module or component within a repository.
  It consists of zero or more **module path folders** followed by a mandatory **module root folder**:
  `[<folders>.]<moduleroot>` (e.g., `mymodule.blfacadeintf`, `tools.profiler`, `build.parent`).
- **Module Root Folder** – the last segment of the module path that identifies the primary module name.
- **Variant** – a suffix identifying a specialized flavor of a module (e.g., `tests`).
- **Artifact** – a build output, typically a JAR published to a Maven repository.

---

### 2.3. Repository Naming

#### 2.3.1 Canonical Form

Repositories MUST be named using the following structure:

```
<vis>.<role>.<BusinessName>[.<reposubname>]
```

Where:

- `<vis>` = `pub` | `priv` (lowercase)
- `<role>` = technical role (lowercase)
- `<BusinessName>` = PascalCase business or domain name
- `<reposubname>` = optional lowercase technical qualifier

#### 2.3.2 Case Rules

- `vis` and `role` MUST be lowercase.
- `BusinessName` MUST be PascalCase.
- All `reposubname` components MUST be lowercase.

#### 2.3.3 Examples

```
pub.pltf.Modustro
priv.app.Modustro
priv.lib.Customers.common
pub.tool.Java
priv.tool.Java
```

---

### 2.4. Artifact Naming (artifactId)

#### 2.4.1 Canonical Form

Each artifactId MUST be composed of:

```
<vis>.<role>.<BusinessName[.reposubname]>_<module.path>[-<variant>]
```

Where:

- the part before `_` identifies the **repository root**,
- the part after `_` identifies the **module within the repository**,
- `<module.path>` follows the form `[<folders>.]<moduleroot>`,
- `-<variant>` is an OPTIONAL suffix identifying a variant of the module root (e.g., `tests`),
- `_` is a mandatory separator between repository identity and module path.

#### 2.4.2 Case Rules

- `<vis>`, `<role>`, `<reposubname>`, all module path folders, module root, and `<variant>` MUST be lowercase.
- `<BusinessName>` MUST preserve PascalCase from the repository name.
- The underscore `_` MUST be used exactly once in the artifactId.
- The variant suffix, if present, MUST be appended using `-`.

#### 2.4.3 Examples

From repository:

```
pub.pltf.Modustro
```

Artifacts:

```
pub.pltf.Modustro_core
pub.pltf.Modustro_languages
pub.pltf.Modustro_mps.plugin
```

From repository:

```
priv.lib.Customers.common
```

Artifacts:

```
priv.lib.Customers.common_aaa.blfacadeintf
priv.lib.Customers.common_aaa.blfacadeintf-tests
priv.lib.Customers.common_aaa.other
```

From repositories:

```
pub.tool.Java
priv.tool.Java
```

Artifacts:

```
pub.tool.Java_build.parent
pub.tool.Java_tools.profiler

priv.tool.Java_build.parent
priv.tool.Java_tools.profiler
```

---

### 2.5. groupId Naming

#### 2.5.1 Canonical Form

groupId MUST follow:

```
eu.algites.<role>.<businessname-lc>[.<reposubname>]
```

Where `<businessname-lc>` is the lowercase form of the BusinessName.

#### 2.5.2 Case Rules

All groupId components MUST be lowercase.

#### 2.5.3 Visibility Rule

Visibility (`pub` | `priv`) MUST NOT be part of the groupId.

The groupId expresses only the **business and technical domain**, not governance.

#### 2.5.4 Examples

```
eu.algites.pltf.modustro
eu.algites.app.modustro.studio
eu.algites.lib.customers
eu.algites.lib.customers.common
eu.algites.tool.java
```

---

### 2.6. Visibility and Artifact Identity

Visibility represents **governance and distribution scope**.

#### 2.6.1 Rules

Visibility MUST:

- be part of the **repository name**,
- be part of the **artifactId**,
- influence CI/CD and publishing targets.

Visibility MUST NOT:

- be part of the **groupId**.

#### 2.6.2 Rationale

Including visibility in the artifactId provides:

- immediate visual distinction in dependency trees and logs,
- early detection of accidental use of private artifacts in public builds,
- clear auditability of JAR files by name alone.

---

### 2.7. Variant Suffix Rules

#### 2.7.1 Purpose

Variants represent specialized flavors of a module root that are published as independent artifacts,
most notably shared test artifacts. It SHOULD be used whenever products that would otherwise be
distinguished only by a build classifier require their transitive dependencies to be tracked and
resolved by Maven/Gradle.

#### 2.7.2 Reserved Variants

The following variant suffixes are RESERVED:

- `tests` – shared tests for the base module root.

Additional variants (e.g., `it`, `bench`) MAY be introduced if documented.

#### 2.7.3 Terminal Rule

Artifacts with variant suffix `-tests` MUST be terminal:

- They MUST NOT define or publish further variants.
- In particular, `*-tests-tests` MUST NOT exist.

This prevents recursive test variants and aligns with Maven idioms where `-tests` denotes a test flavor.

#### 2.7.4 Rationale

The `-tests` suffix intentionally mirrors the common Maven convention
`<artifactId>-tests-<version>.jar` used for test classifiers, while elevating it to a
first-class artifact to enable transitive sharing of test dependencies.

---

### 2.8. Mapping Rules

#### 2.8.1 From Repository to Artifact Root

Given repository:

```
<vis>.<role>.<BusinessName>[.<reposubname>]
```

Artifact root becomes:

```
<vis>.<role>.<BusinessName>[.<reposubname>]
```

and the artifactId is completed by appending:

```
_<module.path>[-<variant>]
```

#### 2.8.2 From BusinessName to groupId

BusinessName is normalized to lowercase:

```
Modustro -> modustro
Customers -> customers
Java -> java
```

---

### 2.9. JAR Naming

The resulting JAR file name MUST follow Maven convention:

```
<artifactId>-<version>.jar
```

Examples:

```
pub.pltf.Modustro_core-1.2.0.jar
priv.lib.Customers.common_bai.blfacadeintf-1.0.0.jar
priv.lib.Customers.common_bai.blfacadeintf-tests-1.0.0.jar
pub.tool.Java_build.parent-1.4.0.jar
```

---

### 2.10. Repository/Artifact Roles (Recommended Set)

Common roles include:

- `pltf` – platform - Algites platform components
- `app` – application - concrete applications
- `lib` – reusable libraries
- `tool` – build and development tools
- `frmw` - framework – generic frameworks
- `lab` – laboratory - experimental or laboratory work

New roles MAY be introduced but MUST be lowercase and documented.

---

### 2.11. CI/CD Governance

CI pipelines MUST:

- infer visibility from repository name prefix (`pub.` vs `priv.`),
- enforce that artifactId starts with the same visibility prefix and follows the `_` separator rule,
- enforce variant rules, including the terminal nature of `-tests`,
- publish artifacts to the corresponding Maven repository:
  - public repo for `pub.*`,
  - private repo for `priv.*`.

groupId MUST remain identical for public and private variants within the same domain.

---

### 2.12. Migration Rule

When migrating legacy projects:

- Preserve BusinessName and structure where possible.
- Align repository names to this standard.
- ArtifactId MUST be adapted to include visibility, the `_` separator,
  and, where applicable, the `-tests` variant suffix.

---

### 2.13. Rationale

This standard balances:

- **Domain identity** (PascalCase BusinessName),
- **Technical structure** (lowercase qualifiers),
- **Practical build compatibility** (lowercase groupId),
- **Governance clarity** (visibility in repository and artifactId),
- **Human auditability** (pub/priv visible in JAR names),
- **Structural readability** (explicit `_` separator between repo and module identity),
- **Maven idioms** (use of `-tests` for test variants),
- **Module semantics** (explicit distinction between module path folders and module root).

It follows proven historical patterns used in legacy conventions such as:

```
lib.Customers.common.aaa.blfacadeintf
```

while extending them with explicit visibility and repository/module separation semantics.

---

### 2.14. Summary

- Repositories express **visibility + role + business identity**.
- Artifacts express **visibility + role + business identity + module specialization**, separated by `_`.
- `<module.path>` is composed of optional path folders and a mandatory module root.
- Optional variant suffixes (e.g., `-tests`) express specialized flavors of the module root.
- `-tests` is terminal and MUST NOT be nested.
- groupId expresses **organizational and domain namespace only**.
- Visibility is part of artifact identity, but not of groupId.
- BusinessName remains PascalCase across repos and artifacts.
- The `_` separator cleanly delineates repository identity from module identity.

This standard is normative for all Algites projects.

---

## 3. Algites Artifact and Dependency Model

This chapter defines the **model-first** structure used by Algites to describe artifacts, versioning, dependency intents, inheritance, and how these concepts are later **mapped** to build tools (Gradle/Maven). The intent is to keep the core model deterministic and transparent, with explicit resolution rules and diagnostics.

---

### 3.1. Goals and Design Principles

- **Deterministic resolution**: given the same model inputs, the resolved graph (versions, intents, outputs) must be identical.
- **Explicit inheritance paths**: distinguish *container inheritance* (contains/includes) from *parent inheritance* (policy chain).
- **Definition vs activation**: catalogs of templates/contexts may be declared broadly, but nothing is effective until activated in a concrete node context.
- **Controlled vs uncontrolled worlds**:
    - *Controlled* artifacts derive versions from a **ContainerVersionContext** (no versions written on references).
    - *Uncontrolled* artifacts carry version rules on dependency intents (ranges + preferred).
- **Early clarity, late validation**: model loading may be lazy; final validation must surface all unresolved references and contradictions.

---

### 3.2. Core Concepts and Terminology

- **Artifact**: a modeled buildable unit (module), producing one or more **outputs**.
- **ArtifactCoordinateId**: a stable identifier for an artifact in the Algites model (not necessarily identical to Maven GAV).
- **OutputType**: a specific output contract of an artifact (e.g., jar, parent-pom, bom, plugin-marker, etc.).
- **Output key (`depKey`)**: `artifactCoordinateId + outputType` used to identify a dependency intent target.
- **Repository configuration**: the root of a repository model, treated as:
    - **root container** (container inheritance origin), and
    - **root parent** (parent inheritance origin).
- **Container edge** (contains/includes): expresses structural ownership/aggregation of artifacts.
- **Parent edge** (parent chain): expresses policy/build inheritance.
- **Dependency intent**: an abstract definition of a dependency with rules describing how it is used/exported.

---

### 3.3. Graph Model

#### 3.3.1 Container Graph (contains/includes)

The **container graph** captures “what is inside what” (repository → containers → artifacts). It is used to inherit:
- **ContainerVersionContext** (for controlled versioning),
- repository-/container-provided catalogs (e.g., rule template sets) when allowed.

Typical operations:
- expand containers into all contained artifacts,
- compute effective container context for each artifact,
- provide a deterministic traversal order for resolution.

#### 3.3.2 Parent Chain (policy inheritance)

The **parent chain** captures “policy inheritance” relationships.
- It is the inheritance channel for **ParentDependencyIntentSets** (baseline dependency intents).
- It can also participate in catalog propagation (e.g., rule template set catalogs), subject to merge rules.

Typical operations:
- for each artifact, compute its effective parent chain (repo-root parent included),
- merge inherited policies deterministically (weight + conflict rules).

#### 3.3.3 Combined Graph Semantics and Validity

Both graphs exist simultaneously and must remain valid:
- **No cycles** in parent chain (a parent cycle is a structural error).
- Container cycles should be forbidden as well (a container loop breaks inheritance).
- A node may be a container and also a parent; this is explicitly supported.
- When both container and parent contribute catalogs, merges must be:
    - **id-based** and deterministic,
    - **conflict-failing** when definitions disagree.

---

### 3.4. Versioning Model

#### 3.4.1 ContainerVersionContext

A **ContainerVersionContext** defines how controlled artifacts acquire versions.
- It can be **defined** at repo or artifact scope (definition layer).
- It becomes effective only when **activated** in a node context (activation layer).

Inheritance rule (normative for v1):
- ContainerVersionContext **inherits only via container edges** (repo → container → contained artifacts).
- Parent chain **does not** change controlled versions.

#### 3.4.2 Controlled vs Uncontrolled Version Resolution

- **Controlled artifacts**
    - Do not specify versions in dependency references.
    - Resolve versions via **effective ContainerVersionContext**.
- **Uncontrolled artifacts**
    - Use version rules attached to dependency intents:
        - `ranges[]` (allowed version set, potentially disjoint),
        - optional `preferred` (concrete choice inside the allowed set),
        - conflict resolution via weight and final validation policy.

Version selection must remain deterministic without querying remote metadata (v1):
- If `preferred` exists and lies in the final allowed set → choose it.
- If no valid preferred exists → the version is unresolved and handled by validation policy.

#### 3.4.3 Diagnostics (source tracing)

For every resolved artifact and dependency intent, the system should be able to report:
- which node provided the effective ContainerVersionContext (repo/container path),
- which rule(s) contributed to the allowed version set,
- which preferred version (if any) won by weight,
- and why a conflict occurred (tie, incompatible ranges, missing preferred under strict mode).

---

### 3.5. Dependency Intent Model

#### 3.5.1 DependencyIntentRule Templates (no versions)

A **DependencyIntentRuleTemplate** is a reusable “shape” (preset) describing dependency behavior **without versioning**.
Examples include:
- Maven-like compile/runtime usage shapes,
- Gradle-like configurations such as `api`, `implementation`, `compileOnly`, `runtimeOnly`,
- test-scoped shapes (develop/test sourceset).

Templates typically configure:
- `exportAs` rule (none/intent/runtime/compile),
- `importAs` rule (none/intent/runtime/compile),
- `applyOnSources` applicability (main/product | test/develop | both),
- `usageSet` set (e.g., classpath item, source processor, build tool, ....).

Each template or rule may define a `weight` (default 0).

#### 3.5.2 DependencyIntentRuleTemplateSets (catalog + merge rules)

A **DependencyIntentRuleTemplateSet** groups multiple rule templates (inline or referenced) under a unique identifier.
- It may live at repo scope, container scope, or parent scope.
- It is part of the **definition layer** (catalog).

Merge semantics:
- If a `templateSetId` appears multiple times:
    - identical definition → OK,
    - different definition → **error** (forces explicit resolution via id change or governance rule).

#### 3.5.3 DependencyIntentTemplateSets (depKey = artifactCoordinateId + outputType)

A **DependencyIntentTemplateSet** defines *concrete dependency intents* by `depKey = artifactCoordinateId + outputType`, each intent referencing:
- one or more **rule template sets** (e.g., `mavenCompile`, `gradleCompileOnly`),
- optional inline **granular rules** (non-version),
- optional **uncontrolled version rules** (`ranges[]`, `preferred`) that apply only to uncontrolled dependencies.

Important: the set itself is still a **template** until activated by an artifact context.

#### 3.5.4 Activation Layer (what is actually applied)

An artifact context decides:
- which ContainerVersionContext is active (if any override),
- which rule template catalogs are available,
- which dependency intent template sets are activated to form the effective dependency set.

Inheritance (v1 intent):
- catalogs (template sets) may merge from repo + container + parent, with id rules,
- **effective dependency intents** (baseline dependencies) flow primarily via parent chain,
- repo-root may inject baseline intents as “virtual parent defaults”.

#### Note: “Optional” and product variability are not modeled as an intent rule

Algites does **not** model Maven’s `<optional>` flag as a first-class dependency intent property.
The Maven optional flag is primarily a *transitive propagation hint* in published metadata
and does not reliably express product availability, runtime presence, or variant semantics.

Instead, Algites models variability and “optional parts” explicitly using **DependencyIntentSets**
and **intent-only exports** (e.g., PIBOM/PVBOM-style outputs). These constructs can express 
“available but not required” capabilities deterministically: they define a catalog of selectable intents,
and consumers decide what to activate in their own contexts.

---

### 3.6. Inheritance and Merge Semantics

#### 3.6.1 What inherits from Container vs Parent

Normative v1 rules:

- **Container inheritance (contains/includes)**
    - ContainerVersionContext activation and overrides,
    - optionally: rule template set catalogs (subject to merge rules).

- **Parent inheritance**
    - ParentDependencyIntentSets (baseline dependency intents),
    - optionally: rule template set catalogs (subject to merge rules),
    - build/publishing conventions that should behave “like parent policy”.

- **Repository config**
    - acts as root container and root parent for everything in the repo.

#### 3.6.2 Conflict Resolution

General strategy:
- scalar fields resolve by `weight` (higher wins),
- equal weight + differing value → **error** (forces explicit user intent),
- set-like fields either:
    - union (additive), or
    - replace-by-weight (if explicitly chosen by policy).

Version rules for uncontrolled:
- allowed set = intersection of allowed sets from all applicable rules (each allowed set may be union of intervals),
- preferred chosen by highest weight among preferred candidates that lie in the final allowed set.

#### 3.6.3 Lazy Loading and Final Validation

- The model may load catalogs and references without immediate existence checks.
- A final validation phase must:
    - ensure all referenced template sets exist,
    - ensure depKeys can be interpreted (at least structurally),
    - ensure no unresolved versions remain under strict publish policies,
    - ensure no rule collisions remain (id collisions, weight ties, incompatible ranges).

---

### 3.7. Outputs and Publication Contracts

Artifacts may produce multiple outputs. An **OutputType** identifies *which* output contract is being referenced (as a dependency target) or published (as an artifact output).

In Algites, an OutputType is **not** a free-form string. It is a **data object** composed of:
- a **builtin output kind** (stable enum), and
- an optional **custom UID** (only when builtin kind is `CUSTOM`).

This makes output typing deterministic, tool-agnostic, and safely extensible.

---

#### 3.7.1 OutputType Data Model

**OutputType fields**
- `builtinOutputKind: AInArtifactBuiltinOutputKind`
- `customUid: String?` (required iff `builtinOutputKind == CUSTOM`)

**Canonical OutputType identifier (`outputTypeUid`)**
- If `builtinOutputKind != CUSTOM`  
  → `outputTypeUid = builtinOutputKind.uid`
- If `builtinOutputKind == CUSTOM`  
  → `outputTypeUid = customUid` (must be non-empty and validated)

**Recommended UID namespace convention**
- Builtin kinds: `builtin:<kind>` (e.g., `builtin:jar`, `builtin:bom`)
- Custom kinds: `custom:<namespaced-id>` (e.g., `custom:eu.algites.output.pibom.v1`)

The exact string format is governed by validation rules; the key requirement is global uniqueness and long-term stability.

---

#### 3.7.2 Builtin Output Kinds

`AInArtifactBuiltinOutputKind` is a stable enum defining the output kinds the system understands natively.
Each enum item MUST have:
- `uid: String` — stable identifier used for comparisons and persistence

Typical builtin kinds (illustrative, not exhaustive, builtin types use the unique id starting with "builtin:"
and the identification is interpreted as complete uid, the custom types must have the uid starting with "custom:"):

- `JAR` (`builtin:jar`) — binary library artifact
- `SOURCES_JAR` (`builtin:sourcesJar`) — sources artifact
- `JAVADOC_JAR` (`builtin:javadocJar`) — javadoc artifact
- `MAVEN_POM` (`builtin:mavenPom`) — standard module POM publication
- `PARENT_POM` (`builtin:parentPom`) — parent-style POM (inheritance contract)
- `BOM` (`builtin:bom`) — dependency steering output (constraints/catalog)
- `PLUGIN_MARKER` (`builtin:pluginMarker`) — Gradle plugin marker publication
- `CUSTOM` (`custom:`) — extension point for non-builtin output kinds; the value is used 
                         as the UID prefix for custom identifications



Notes:
- Builtin kinds are intended to be *minimal but sufficient* for Algites v1.
- Adding new builtin kinds is a compatibility-sensitive change; prefer `CUSTOM` when possible.

---

#### 3.7.3 Custom Output Kinds

Custom output kinds are represented by:
- `builtinOutputKind = CUSTOM`
- `customUid = <non-empty, globally unique UID>`

Custom UIDs MUST be:
- globally unique within the model space
- stable across time (do not rename once published)
- validation-safe (restricted character set / pattern)

Custom outputs are especially useful for:
- project-specific BOM variants (e.g., PIBOM/PVBOM-style outputs)
- tool metadata outputs not covered by builtins
- transitional outputs during migrations

---

#### 3.7.4 OutputType Interface Contract

If implemented as an interface, the minimal contract is:

- `AIiArtifactOutputType.getBuiltinOutputKind(): AInArtifactBuiltinOutputKind`
- `AIiArtifactOutputType.getOutputTypeUid(): String`

Normative behavior:
- `getOutputTypeUid()` MUST return:
    - the builtin UID when builtin kind is not `CUSTOM`, or
    - the custom UID when builtin kind is `CUSTOM`

Implementations MUST NOT invent additional derivation rules beyond the above.

---

#### 3.7.5 How OutputType Is Used in Dependencies

Dependency intents reference outputs via the **dependency key**:

- `depKey = artifactCoordinateId + outputType`

This enables a dependency to target *specific* contracts, for example:
- a `PARENT_POM` output as the parent-like inheritance contract
- a `BOM` output as dependency steering (constraints) input
- a `JAR` output for normal library consumption

Normative rule:
- When an intent must distinguish contracts, it MUST specify the correct `outputType` rather than using implicit defaults.

---

#### 3.7.6 Publication Contracts and Deterministic Mapping

Publication configuration MUST map OutputTypes deterministically to build-tool publication mechanisms.

Examples (tool mapping is defined in section 3.8):
- `JAR` → Maven/Gradle main publication artifact (binary)
- `SOURCES_JAR`, `JAVADOC_JAR` → classifier artifacts / additional publications
- `MAVEN_POM`, `PARENT_POM` → POM publications with specific semantics
- `BOM` → Gradle platform/constraints and/or Maven dependencyManagement/BOM import
- `PLUGIN_MARKER` → Gradle plugin marker publication

Custom outputs:
- MUST define their mapping policy explicitly (by build policy / mapping layer)
- SHOULD fail fast if no mapping exists for the target build tool/output format

---

#### 3.7.7 Validation Rules (Normative)

1. If `builtinOutputKind != CUSTOM`
    - `customUid` MUST be absent/empty.
2. If `builtinOutputKind == CUSTOM`
    - `customUid` MUST be present and non-empty.
3. `outputTypeUid` MUST be stable and globally unique within the model.
4. Any dependency intent that references an OutputType MUST reference a resolvable output kind for the target artifact.
5. Mapping MUST fail fast if an output kind cannot be mapped to the selected build tool or publication mode (unless a policy explicitly allows ignoring it).

---

### 3.8. Mapping to Build Tools

#### 3.8.1 Gradle mapping

- Intent rule templates map to Gradle configurations:
    - `api`, `implementation`, `compileOnly`, `runtimeOnly`,
    - test variants: `testImplementation`, `testRuntimeOnly`, etc.
- Dependency steering maps to:
    - `platform(...)` / `enforcedPlatform(...)`,
    - Gradle constraints and dependency locking strategies (when used).

#### 3.8.2 Maven mapping

- Intent rules map to Maven scopes:
    - compile/runtime/provided/test (and/or plugin-related constructs),
- Dependency steering maps to `dependencyManagement` (including BOM imports),
- Parent chain mapping (v1): internal-only; external parent import is deferred.

#### Maven `<optional>`: mapping-only, not a semantic source of truth

Maven’s `<optional>true</optional>` is treated as a **mapping artifact** only. In Maven, `optional`
mainly means: “do not propagate this dependency transitively to downstream consumers.” 
It does **not** define a product variant, does not guarantee runtime availability, 
and does not express deterministic “feature selection”.

Therefore, Algites does not use Maven optional as the semantic basis for “optional components”. 
Instead, optionality/variability is represented via:
- **DependencyIntentSets** (explicit activation sets), and
- **intent-only exports** (PIBOM/PVBOM-style outputs) that provide 
- a selectable catalog of intents.

If Maven optional emission is needed for compatibility with Maven consumers, it may be generated
as a **best-effort metadata hint** by the Maven mapping layer. Such emission must not change
the Algites semantic model and must not be relied upon as the primary expression of product composition.

Normative rule (v1): Algites MUST NOT require Maven `<optional>` to preserve correctness 
of the resolved model; optionality MUST be expressed via intent sets / intent-only exports,
not via Maven optional.

#### 3.8.3 Known semantic mismatches

- Gradle distinguishes API exposure vs internal usage more explicitly (`api` vs `implementation`).
- Maven’s scope system is coarser; mapping requires careful interpretation of “export”.
- Source processing (annotation processors / kapt) has tool-specific behavior.

Algites mapping must be documented per OutputType and intent rule preset.

---

### 3.9. Repository Structure and Build Integration

Practical repository expectations (v1):
- Repository config provides:
    - default ContainerVersionContext (if the repo is controlled),
    - baseline dependency intents (if desired),
    - catalogs of rule templates and dependency intent templates.

Build integration:
- settings.gradle.kts: project structure and inclusion rules.
- build.gradle.kts: build behavior, publishing definitions, and mapping of model resolution to tool configuration.
- CI: supplies publishing targets (download/upload endpoints) via environment/properties consistent with Algites conventions.

---

### 3.10. Migration Notes (temporary; will be removed)

This section is intentionally placed at the end and is **temporary**.

- Previous models that relied on **ArtifactKind** (policy/bom/aggregator/etc.) should be migrated by:
    - replacing “kind-driven behavior” with explicit **ContainerVersionContext** + **DependencyIntent** activation,
    - moving BOM/policy logic into **OutputType** + intent rule templates for dependency steering.
- Aggregator artifacts remain possible, but are no longer mandatory just to express policy inheritance:
    - repo-root and containers can supply catalogs,
    - parent edges define baseline dependency intents.

---

### 3.11. Mermaid Diagrams (Normative Reference)

#### 3.11.1 Container vs Parent inheritance overview

```mermaid
flowchart TD
  R[Repo Config<br/>(root container + root parent)]
  C1[Container Artifact A1]
  B1[Artifact B1]
  B2[Artifact B2]
  P1[Parent Artifact A2]

  R -->|contains| C1
  C1 -->|contains| B1
  C1 -->|contains| B2

  B1 -->|parent| P1

  VC[ContainerVersionContext]
  RT[RuleTemplate catalog]
  DI[ParentDependencyIntentSets]

  R --> VC
  VC -->|inherits via contains| C1
  C1 -->|inherits via contains| B1
  C1 -->|inherits via contains| B2

  R --> RT
  RT -->|merge allowed| C1
  RT -->|merge allowed| P1
  RT -->|merge allowed| B1

  R --> DI
  DI -->|inherits via parent| P1
  P1 -->|inherits via parent| B1
```

#### 3.11.2 Resolution pipeline (high level)

```mermaid
flowchart LR
  A[Load definitions<br/>(repo + artifacts)] --> B[Merge catalogs<br/>(template sets)]
  B --> C[Compute container contexts<br/>(ContainerVersionContext)]
  C --> D[Compute parent contexts<br/>(baseline intents)]
  D --> E[Activate intents<br/>(apply templates + rules)]
  E --> F[Resolve versions<br/>(controlled via VC;<br/>uncontrolled via ranges/preferred)]
  F --> G[Final validation<br/>(conflicts, unresolved, cycles)]
  G --> H[Generate build-tool outputs<br/>(Gradle/Maven mapping)]
```

[[/FINAL-APPROVED]]

