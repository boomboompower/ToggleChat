package me.boomboompower.togglechat.toggles.custom;

import me.boomboompower.togglechat.toggles.custom.conditions.*;

import java.util.List;

public abstract class ToggleCondition implements Function<String, Boolean> {

    private String text;

    public ToggleCondition(String input) {
        this.text = input;
    }

    public String getText() {
        return this.text;
    }

    public static ToggleCondition get(String input) {
        if (isEmpty(input)) {
            return new ConditionEmpty();
        }

        String condName = input.substring(0, input.indexOf("(")).toLowerCase();
        String[] arguments = input.substring(input.indexOf("(") + 1, input.lastIndexOf(")")).split(",");
        switch (condName) {
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

    public static TypeCustom createCustomToggle(String name, List<ToggleCondition> condition) {
        return new TypeCustom(name, condition);
    }
}
