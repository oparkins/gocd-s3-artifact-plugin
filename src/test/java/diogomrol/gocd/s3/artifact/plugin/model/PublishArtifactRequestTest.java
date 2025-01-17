/*
 * Copyright 2018 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package diogomrol.gocd.s3.artifact.plugin.model;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class PublishArtifactRequestTest {

    @Test
    public void shouldDeserializeRequestBody() {
        final String json = "{\n" +
                "  \"artifact_plan\": {\n" +
                "    \"configuration\": {\n" +
                "      \"Source\": \"alpine-build.json\"\n" +
                "    },\n" +
                "    \"id\": \"installers\",\n" +
                "    \"storeId\": \"s3-store\"\n" +
                "  },\n" +
                "  \"artifact_store\": {\n" +
                "    \"configuration\": {\n" +
                "      \"S3Bucket\": \"s3-url\",\n" +
                "      \"Region\": \"us-west-1\",\n" +
                "      \"AWSAccessKey\": \"aws-access-key\",\n" +
                "      \"AWSSecretAccessKey\": \"aws-secret-access-key\",\n" +
                "      \"EndpointURL\": \"https://s3.us-west-1.amazonaws.com\",\n" +
                "      \"PathStyleAccess\": false\n" +
                "    },\n" +
                "    \"id\": \"s3-store\"\n" +
                "  },\n" +
                "  \"agent_working_directory\": \"/temp\"\n" +
                "}";

        final PublishArtifactRequest publishArtifactRequest = PublishArtifactRequest.fromJSON(json);

        assertThat(publishArtifactRequest.getAgentWorkingDir()).isEqualTo("/temp");

        assertThat(publishArtifactRequest.getArtifactStore().getId()).isEqualTo("s3-store");
        assertThat(publishArtifactRequest.getArtifactStore().getArtifactStoreConfig())
                .isEqualTo(new ArtifactStoreConfig("s3-url", "us-west-1", "aws-access-key", "aws-secret-access-key", "https://s3.us-west-1.amazonaws.com", false));

        assertThat(publishArtifactRequest.getArtifactPlan())
                .isEqualTo(new ArtifactPlan("installers", "s3-store", "alpine-build.json", Optional.empty()));
    }
}