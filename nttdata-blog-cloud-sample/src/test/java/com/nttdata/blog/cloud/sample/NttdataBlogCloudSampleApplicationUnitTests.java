package com.nttdata.blog.cloud.sample;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = NttdataBlogCloudSampleApplication.class)
class NttdataBlogCloudSampleApplicationUnitTests {

	private final NttdataBlogCloudSampleApplication nttdataBlogCloudSampleApplication;

	@Autowired
	public NttdataBlogCloudSampleApplicationUnitTests(NttdataBlogCloudSampleApplication nttdataBlogCloudSampleApplication) {
		this.nttdataBlogCloudSampleApplication = nttdataBlogCloudSampleApplication;
	}

	@Test
	void contextLoads(){
		Assertions.assertThat(nttdataBlogCloudSampleApplication).isNotNull();
	}

}
