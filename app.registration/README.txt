OpenShift Online provides an S2I builder image for building Java applications. This builder image takes your application
source or binary artifacts, builds the source using Maven (if source was provided), and assembles the artifacts with any
required dependencies to create a new, ready-to-run image containing your Java application. This resulting image can be
run on OpenShift Online or run directly with Docker.

The RHEL 7 S2I Builder Image is available through the Red Hat Registry:
=======================================================================

docker pull registry.access.redhat.com/redhat-openjdk-18/openjdk18-openshift

COMMON PROBLEMS:
Trying to pull repository registry.access.redhat.com/redhat-openjdk-18/openjdk18-openshift ...
open /etc/docker/certs.d/registry.access.redhat.com/redhat-ca.crt: no such file or directory

  My work around is by creating an empty redhat-uep.pem file.
  cd /etc/rhsm/ca/
  touch redhat-uep.pem


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

Create the image stream from the JSON
=====================================

oc create -f openjdk-s2i-imagestream.json



To create a new JAVA application bundled as part of the above Java S2I image:
=============================================================================

oc new-app redhat-openjdk18-openshift~https://github.com/jboss-openshift/openshift-quickstarts --context-dir=undertow-servlet

oc new-app redhat-openjdk18-openshift~https://github.com/rafourie/asapp --context-dir=rest.asapp




Expose port 8083 of the newly created service:
==============================================
oc expose svc asapp


Redirect traffic to the container on port 8080
==============================================
oc patch svc asapp -p '{"spec":{"externalIPs":["192.174.120.10"]}}'

ip address add 192.168.120.10 dev eth0

netstat -nr
Kernel IP routing table
Destination     Gateway         Genmask         Flags   MSS Window  irtt Iface
0.0.0.0         197.242.144.1   0.0.0.0         UG        0 0          0 eth0
10.128.0.0      0.0.0.0         255.252.0.0     U         0 0          0 tun0
172.17.0.0      0.0.0.0         255.255.0.0     U         0 0          0 docker0
172.30.0.0      0.0.0.0         255.255.0.0     U         0 0          0 tun0
197.242.144.0   0.0.0.0         255.255.248.0   U         0 0          0 eth0

route add -host 197.242.149.160 gw 197.242.144.1 dev eth0

route add -net 192.174.120.0/24 gw 197.242.144.1 eth0


172.30.74.8
ip address add 172.30.74.8 dev eth0
route add -net 172.30.74.0/24 gw 197.242.144.1 eth0



==================================================================
Retrieve a list of addresses for pods running for a single service
==================================================================

oadm policy who-can create clusterrolebinding

system:admin

oc login -u system:admin https://console.197.242.149.160.xip.io:8443/

[root@primekube ~]# kubectl create clusterrolebinding admin --clusterrole=cluster-admin --serviceaccount=default:default
Error from server (AlreadyExists): clusterrolebindings.rbac.authorization.k8s.io "admin" already exists

[root@primekube ~]# kubectl create clusterrolebinding root --clusterrole=cluster-admin --serviceaccount=default:default
clusterrolebinding.rbac.authorization.k8s.io/root created


# If you deploy your microservices on Minishift, you should first enable admin-user add-on, then log in as a cluster
# admin and grant the required permissions.

[root@primekube ~]# oc policy add-role-to-user cluster-reader system:serviceaccount:vodacom-poc:default
role "cluster-reader" added: "system:serviceaccount:vodacom-poc:default"

