package eu.algites.tool.build.model.utils;

import eu.algites.tool.build.model.loader.AIcArtifactModelLoader;

import java.nio.file.Path;
import java.util.List;

/**
 * <p>
 * Title: {@link eu.algites.tool.build.model.utils.AInConfigurationFileKind}
 * </p>
 * <p>
 * Description: Supported kinds of the configuration for loading the artifact model
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 09.01.26 12:23
 */
public enum AInConfigurationFileKind {
	YAML("loader", "yml"),
	JSON("json"),
	;

	/**
	 * Returns the expected default kind, wqhcih is {@link #YAML}.
	 * @return default kind - {@link #YAML}
	 */
	public static AInConfigurationFileKind getDefaultKind() {
		return YAML;
	}

	/**
	 * Gets the default extension used by the given configuration file kind
	 * @return the default extension
	 */
	public String getDefaultExtension() {
		return configurationFileExtensionPossibilities.get(0);
	}

	/**
	 * @return the configurationFileExceptionPossibilities
	 */
	public List<String> getConfigurationFileExtensionPossibilities() {
		return configurationFileExtensionPossibilities;
	}

	private final List<String> configurationFileExtensionPossibilities;

	AInConfigurationFileKind(String... aConfigurationFileExtensionPossibilities) {
		configurationFileExtensionPossibilities = List.of(aConfigurationFileExtensionPossibilities);
	}

	public static AInConfigurationFileKind fromPath(Path aFileName) {
		String locPathValue = aFileName.toString();
		String locExtension = locPathValue.substring(locPathValue.lastIndexOf('.') + 1).toLowerCase();
		for (AInConfigurationFileKind locResult : values())
			for (String locAllowedExtension : locResult.configurationFileExtensionPossibilities)
				if (locExtension.equalsIgnoreCase(locAllowedExtension))
					return locResult;
		throw new IllegalArgumentException("Unknown artifact source: " + locExtension);
	}
}
