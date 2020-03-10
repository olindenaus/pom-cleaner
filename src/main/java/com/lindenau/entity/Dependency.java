package com.lindenau.entity;

import java.util.Objects;

public class Dependency implements Comparable {
    private String artifactId;
    private String groupId;
    private String version;
    private String special;

    public String getArtifactId() {
        return artifactId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getVersion() {
        return version;
    }

    public String getSpecial() {
        return special;
    }

    public Dependency(DependencyBuilder db) {
        this.artifactId = Objects.requireNonNull(db.artifactId);
        this.groupId = Objects.requireNonNull(db.groupId);
        this.version = Objects.requireNonNull(db.version);
        this.special = db.special;
    }

    public static DependencyBuilder builder() {
        return new DependencyBuilder();
    }

    @Override
    public String toString() {
        return "<dependency>\n" +
                "<artifactId>" + artifactId + "</artifactId>\n" +
                "<groupId>" + groupId + "</groupId>\n" +
                "<version>" + version + "</version>\n" +
                getSpecialPart() +
                "</dependency>\n";
    }

    public String getSpecialPart() {
        return special.isEmpty() ? "" : special + "\n";
    }

    @Override
    public int compareTo(Object o) {
        return this.getArtifactId().compareTo(((Dependency) o).getArtifactId());
    }

    public static class DependencyBuilder {
        private String artifactId;
        private String groupId;
        private String version;
        private String special;

        public DependencyBuilder withArtifactId(String artifactId) {
            this.artifactId = artifactId;
            return this;
        }

        public DependencyBuilder withGroupId(String groupId) {
            this.groupId = groupId;
            return this;
        }

        public DependencyBuilder withVersion(String version) {
            this.version = version;
            return this;
        }

        public DependencyBuilder withSpecial(String special) {
            this.special = special;
            return this;
        }

        public Dependency build() {
            return new Dependency(this);
        }
    }
}
