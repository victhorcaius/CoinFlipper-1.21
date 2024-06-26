package io.github.gronnmann.coinflipper.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandCompleter implements TabCompleter {
    //Label and perm
    HashMap<String, String> commands;
    public CommandCompleter(List<CommandModule> modules) {
        commands = new HashMap<>();
        for (CommandModule module : modules) {
            for (String label : module.getLabels()) {
                commands.put(label, module.getPermission());
            }
        }
    }
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> result = new ArrayList<>();
        if (strings.length == 1) {
            List<String> completions = new ArrayList<>();
            for (String key : commands.keySet()) {
                if (!commandSender.hasPermission("coinflipper." + commands.get(key))) {
                    continue;
                }
                completions.add(key);
            }

            StringUtil.copyPartialMatches(strings[0], completions, result);
            return result;
        }
        if (strings.length == 2) {
            if (strings[0].equalsIgnoreCase("create")) {
                List<String> completions = new ArrayList<>();
                completions.add("100");
                StringUtil.copyPartialMatches(strings[1], completions, result);
                return result;
            }
        }
        if (strings.length == 3) {
            if (strings[0].equalsIgnoreCase("create")) {
                List<String> completions = new ArrayList<>();
                completions.add("heads");
                completions.add("tails");
                StringUtil.copyPartialMatches(strings[2], completions, result);
                return result;
            }
        }
        return null;
    }
}
