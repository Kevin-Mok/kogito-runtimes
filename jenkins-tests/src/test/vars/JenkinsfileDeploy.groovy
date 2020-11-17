import com.homeaway.devtools.jenkins.testing.JenkinsPipelineSpecification

class JenkinsfileDeploy extends JenkinsPipelineSpecification {
	def Jenkinsfile = null

    def setup() {
        Jenkinsfile = loadPipelineScriptForTest('Jenkinsfile.deploy')
        explicitlyMockPipelineVariable('githubscm')
    }

	def '[Jenkinsfile.deploy] getDefaultBranch: no PR_TARGET_BRANCH' () {
		setup:
            Jenkinsfile.getBinding().setVariable('params', ['PR_TARGET_BRANCH' : ''])
		when:
			def branch = Jenkinsfile.getDefaultBranch('repo')
		then:
            branch == 'master'
	}

	def '[Jenkinsfile.deploy] getDefaultBranch: PR branch exists' () {
		setup:
            Jenkinsfile.getBinding().setVariable('params', ['PR_TARGET_BRANCH' : 'target-branch'])
            getPipelineMock('githubscm.getRepositoryScm')('repo', 'kiegroup', 'target-branch') >> 'branch'
		when:
			def branch = Jenkinsfile.getDefaultBranch('repo')
		then:
            branch == 'target-branch'
	}

	def '[Jenkinsfile.deploy] getDefaultBranch: PR branch doesn\'t exist' () {
		setup:
            Jenkinsfile.getBinding().setVariable('params', ['PR_TARGET_BRANCH' : 'target-branch'])
            getPipelineMock('githubscm.getRepositoryScm')('repo', 'kiegroup', 'target-branch') >> null
		when:
			def branch = Jenkinsfile.getDefaultBranch('repo')
		then:
            branch == 'master'
	}

	def '[Jenkinsfile.deploy] getMavenRepoZipUrl: no trailing slash' () {
		setup:
            Jenkinsfile.getBinding().setVariable('params', ['MAVEN_DEPLOY_REPOSITORY' : 'http://nexus-url.com/nexus/content/repositories/test-repo'])
		when:
			def value = Jenkinsfile.getMavenRepoZipUrl()
		then:
            value == 'http://nexus-url.com/nexus/service/local/repositories/test-repo/content-compressed'
	}

	def '[Jenkinsfile.deploy] getMavenRepoZipUrl: trailing slash' () {
		setup:
            Jenkinsfile.getBinding().setVariable('params', ['MAVEN_DEPLOY_REPOSITORY' : 'http://nexus-url.com/nexus/content/repositories/test-repo/'])
		when:
			def value = Jenkinsfile.getMavenRepoZipUrl()
		then:
            value == 'http://nexus-url.com/nexus/service/local/repositories/test-repo/content-compressed'
	}
}
