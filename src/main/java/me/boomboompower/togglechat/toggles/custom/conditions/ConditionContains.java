package me.boomboompower.togglechat.toggles.custom.conditions;

import me.boomboompower.togglechat.toggles.custom.ToggleCondition;

/**
 * This code was created by OrangeMarshall and used
 *          with permission by boomboompower.
 *
 * Full credit to OrangeMarshall
 *
 * @author OrangeMarshall
 */
public class ConditionContains extends ToggleCondition {

    private int matchCount = 1;

    public ConditionContains(String input) {
        super(input);
    }

    @Override
    public String getSaveIdentifier() {
        return "contains";
    }

    public ConditionContains(String input, int matchCount) {
        super(input);
        this.matchCount = Math.max(1, matchCount);
    }

    @Override
    public Boolean apply(String input) {
        return countMatches(input, getText()) == this.matchCount;
    }

    private int countMatches(String str, String sub) {
        if (isEmpty(str) || isEmpty(sub)) {
            return 0;
        }

        int count = 0;
        int idx = 0;

        while ((idx = str.indexOf(sub, idx)) != -1) {
            count++;
            idx += sub.length();
        }
        return count;
    }
}
