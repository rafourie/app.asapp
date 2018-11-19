OpenShift Online provides an S2I builder image for building Java applications. This builder image takes your application
source or binary artifacts, builds the source using Maven (if source was provided), and assembles the artifacts with any
required dependencies to create a new, ready-to-run image containing your Java application. This resulting image can be
run on OpenShift Online or run directly with Docker.

The RHEL 7 S2I Builder Image is available through the Red Hat Registry:
=======================================================================

docker pull registry.access.redhat.com/redhat-openjdk-18/openjdk18-openshift


Before we start using the Java S2I image we need to tell OpenShift how to find it. This is done by creating an image
stream. This can be done from the command line running:

oc create -f openjdk-s2i-imagestream.json


openjdk-s2i-imagestream.json
============================

{
    "kind": "ImageStream",
    "apiVersion": "v1",
    "metadata": {
        "name": "redhat-openjdk18-openshift"
    },
    "spec": {
        "dockerImageRepository": "registry.access.redhat.com/redhat-openjdk-18/openjdk18-openshift",
        "tags": [
            {
                "name": "1.0",
                "annotations": {
                    "description": "OpenJDK S2I images.",
                    "iconClass": "icon-jboss",
                    "tags": "builder,java,xpaas",
                    "supports":"java:8,xpaas:1.0",
                    "sampleRepo": "https://github.com/jboss-openshift/openshift-quickstarts",
                    "sampleContextDir": "undertow-servlet",
                    "version": "1.0"
                }
            }
        ]
    }
}


To create a new JAVA application bundled as part of the above Java S2I image:
=============================================================================

oc new-app redhat-openjdk18-openshift~https://github.com/jboss-openshift/openshift-quickstarts --context-dir=undertow-servlet

oc new-app redhat-openjdk18-openshift~https://github.com/rafourie/asapp --context-dir=rest.asapp


Expose port 8083 of the newly created service:
==============================================
oc expose svc asapp

