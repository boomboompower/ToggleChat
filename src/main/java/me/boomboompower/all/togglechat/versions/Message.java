/*
 *     Copyright (C) 2016 boomboompower
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.boomboompower.all.togglechat.versions;

import me.boomboompower.all.togglechat.ToggleChat;

import scala.actors.threadpool.Arrays;

import java.util.List;

public class Message {

    private static Message instance;

    private String topic;
    private Integer revision;
    private List<String> about;

    public Message(String topic, Integer revision, String... message) {
        this(topic, revision, Arrays.asList(message));
    }

    public Message(String topic, Integer revision, List<String> message) {
        this.topic = topic;
        this.about = message;
        this.revision = revision;

        doMagic();

        instance = this;
    }

    private void doMagic() {
        ToggleChat.updatedStartupRevision = revision;

        if (ToggleChat.statupMessageRevision < ToggleChat.updatedStartupRevision) {
            ToggleChat.showStatupMessage = true;
        }
    }

    public String getTopic() {
        return topic != null ? topic : "an unknown topic";
    }

    public Integer getRevision() {
        return revision;
    }

    public List<String> getMessages() {
        return about;
    }

    public Message getInstance() {
        return instance;
    }
}
