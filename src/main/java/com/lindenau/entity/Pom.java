package com.lindenau.entity;

import java.io.ObjectInputStream;
import java.util.Objects;

public class Pom {

    private String artifactId;
    private String groupId;
    private boolean hasParent;
    private String parentArtifactId;

    public String getArtifactId() {
        return artifactId;
    }

    public String getGroupId() {
        return groupId;
    }

    public boolean isHasParent() {
        return hasParent;
    }

    public String getParentArtifactId() {
        return parentArtifactId;
    }

    private Pom(PomBuilder pb) {
        this.artifactId = Objects.requireNonNull(pb.artifactId);
        this.groupId = Objects.requireNonNull(pb.groupId);
        this.hasParent = !pb.parentArtifactId.isEmpty();
        this.parentArtifactId = pb.parentArtifactId;
    }

    public static PomBuilder builder() {
        return new PomBuilder();
    }

    public static class PomBuilder {
        private String artifactId;
        private String groupId;
        private String parentArtifactId = "";

        private PomBuilder() {
        }

        public PomBuilder withArtifactId(String artifactId) {
            this.artifactId = artifactId;
            return this;
        }

        public PomBuilder withGroupId(String groupId) {
            this.groupId = groupId;
            return this;
        }

        public PomBuilder withParentArtifactId(String parentArtifactId) {
            this.parentArtifactId = parentArtifactId;
            return this;
        }

        public Pom build() {
            return new Pom(this);
        }
    }
}
