package run.slicer.procyon.impl;

import com.strobel.decompiler.DecompilerSettings;

import java.util.HashMap;
import java.util.Map;

public final class ProcyonOptions {
    public static Map<String, String> DEFAULTS = Map.ofEntries(
            Map.entry("include_line_numbers_in_bytecode", "true"),
            Map.entry("show_synthetic_members", "false"),
            Map.entry("always_generate_exception_variable_for_catch_blocks", "true"),
            Map.entry("force_fully_qualified_references", "false"),
            Map.entry("force_explicit_imports", "true"),
            Map.entry("force_explicit_type_arguments", "false"),
            Map.entry("flatten_switch_blocks", "false"),
            Map.entry("exclude_nested_types", "false"),
            Map.entry("retain_redundant_casts", "false"),
            Map.entry("retain_pointless_switches", "false"),
            Map.entry("unicode_output_enabled", "false"),
            Map.entry("include_error_diagnostics", "true"),
            Map.entry("merge_variables", "false"),
            Map.entry("disable_for_each_transforms", "false"),
            Map.entry("show_debug_line_numbers", "false"),
            Map.entry("simplify_member_references", "false"),
            Map.entry("preview_features_enabled", "false"),
            Map.entry("text_block_line_minimum", "3")
    );

    private ProcyonOptions() {
    }

    public static DecompilerSettings toProcyon(Map<String, String> options) {
        final var settings = new DecompilerSettings();
        for (final Map.Entry<String, String> entry : options.entrySet()) {
            switch (entry.getKey()) {
                case "include_line_numbers_in_bytecode" ->
                        settings.setIncludeLineNumbersInBytecode(Boolean.parseBoolean(entry.getValue()));
                case "show_synthetic_members" ->
                        settings.setShowSyntheticMembers(Boolean.parseBoolean(entry.getValue()));
                case "always_generate_exception_variable_for_catch_blocks" ->
                        settings.setAlwaysGenerateExceptionVariableForCatchBlocks(Boolean.parseBoolean(entry.getValue()));
                case "force_fully_qualified_references" ->
                        settings.setForceFullyQualifiedReferences(Boolean.parseBoolean(entry.getValue()));
                case "force_explicit_imports" ->
                        settings.setForceExplicitImports(Boolean.parseBoolean(entry.getValue()));
                case "force_explicit_type_arguments" ->
                        settings.setForceExplicitTypeArguments(Boolean.parseBoolean(entry.getValue()));
                case "flatten_switch_blocks" -> settings.setFlattenSwitchBlocks(Boolean.parseBoolean(entry.getValue()));
                case "exclude_nested_types" -> settings.setExcludeNestedTypes(Boolean.parseBoolean(entry.getValue()));
                case "retain_redundant_casts" ->
                        settings.setRetainRedundantCasts(Boolean.parseBoolean(entry.getValue()));
                case "retain_pointless_switches" ->
                        settings.setRetainPointlessSwitches(Boolean.parseBoolean(entry.getValue()));
                case "unicode_output_enabled" ->
                        settings.setUnicodeOutputEnabled(Boolean.parseBoolean(entry.getValue()));
                case "include_error_diagnostics" ->
                        settings.setIncludeErrorDiagnostics(Boolean.parseBoolean(entry.getValue()));
                case "merge_variables" -> settings.setMergeVariables(Boolean.parseBoolean(entry.getValue()));
                case "disable_for_each_transforms" ->
                        settings.setDisableForEachTransforms(Boolean.parseBoolean(entry.getValue()));
                case "show_debug_line_numbers" ->
                        settings.setShowDebugLineNumbers(Boolean.parseBoolean(entry.getValue()));
                case "simplify_member_references" ->
                        settings.setSimplifyMemberReferences(Boolean.parseBoolean(entry.getValue()));
                case "preview_features_enabled" ->
                        settings.setPreviewFeaturesEnabled(Boolean.parseBoolean(entry.getValue()));
                case "text_block_line_minimum" -> settings.setTextBlockLineMinimum(Integer.parseInt(entry.getValue()));
            }
        }

        return settings;
    }

    public static DecompilerSettings toProcyonWithDefaults(Map<String, String> options) {
        final Map<String, String> newOptions = new HashMap<>(DEFAULTS);
        newOptions.putAll(options);

        return toProcyon(newOptions);
    }
}
