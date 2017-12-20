package me.boomboompower.togglechat.toggles.custom;

import me.boomboompower.togglechat.toggles.custom.conditions.*;

import java.util.List;

public abstract class ToggleCondition implements Function<String, Boolean> {

    private String name;
    private String text;

    public ToggleCondition(String input) {
        this.text = input;
    }

    public abstract String getSaveIdentifier();

    public final String getText() {
        return this.text;
    }

    public static ToggleCondition get(String input) {
        if (isEmpty(input)) {
            return new ConditionEmpty();
        }

        String condName = input.substring(0, input.indexOf("(")).toLowerCase();
        String[] arguments = input.substring(input.indexOf("(") + 1, input.lastIndexOf(")")).split(",");
        try {
            switch (condName.toLowerCase()) {
                case "startswith":
                    return new ConditionStartsWith(arguments[0]);
                case "endswith":
                    return new ConditionEndsWith(arguments[0]);
                case "equals":
                    return new ConditionEquals(arguments[0]);
                case "equalsignorecase":
                    return new ConditionEqualsIgnoreCase(arguments[0]);
                case "contains":
                    if (arguments.length == 1) {
                        return new ConditionContains(arguments[0]);
                    }
                    return new ConditionContains(arguments[0], parseInt(arguments[1]));
                case "regex":
                    return new ConditionRegex(arguments[0]);
            }
        } catch (Exception ex) {
            System.out.println(String.format("[ToggleCondition] Failed to load custom toggle: Input = [ \"%s\" ]", input));
            // If there is an issue, we'll return nothing
        }
        return new ConditionEmpty();
    }

    protected static boolean isEmpty(String input) {
        return input == null || input.isEmpty();
    }

    protected static int parseInt(String input) {
        try {
            if (!isEmpty(input)) {
                return Integer.parseInt(input);
            }
        } catch (Exception ex) {
        }
        return 1;
    }

    public static boolean isValidFormat(String line) {
        return !line.startsWith("//") && !line.isEmpty() && line.contains(" : ") && !(get(line.split(" : ")[1]) instanceof ConditionEmpty);
    }

    public static String getFormatName(String line) {
        if (isValidFormat(line)) {
            return line.split(" : ")[0];
        } else {
            return null;
        }
    }

    public static TypeCustom createCustomToggle(String name, List<ToggleCondition> condition) {
        return new TypeCustom(name, condition);
    }
}
