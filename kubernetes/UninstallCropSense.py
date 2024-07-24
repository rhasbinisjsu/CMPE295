## CropSense ecosystem kubernetes teardown automation script

## Imported modules
import subprocess

## --- Helper functions ---

# Delete a kuberneter resource by name
def kubectlDeleteResourceCmd(resourceKind, csSvc):
    deleteCmd = "kubectl delete " + resourceKind + " " + csSvc
    print("[ INFO ] Running command: " + deleteCmd)
    output = subprocess.run(deleteCmd, shell=True, capture_output=True)
    return output


## --- Tear down process ---

print("\n\n-------- ENTERING TEAR-DOWN PROCESS --------\n\n")

deploymentNames = [
    "cropsense-application-server",
    "cropsense-metrics-server"
]

print("[ INFO ] Deployment resouces to tear down: " + str(deploymentNames))

serviceNames = [
    "cropsense-application-server-service",
    "cropsense-metrics-server-service"
]

print("\n[ INFO ] Service resources to tear down: " + str(serviceNames))

configmapNames = [
    "cropsense-application-server-configmap",
    "cropsense-metrics-server-configmap"
]

print("\n[ INFO ] Configmap resources to tear down: " + str(configmapNames))

for deployment in deploymentNames:
    print("\n[ INFO ] Tearing down deployment: " + deployment)
    output = kubectlDeleteResourceCmd("deployment", deployment)
    if output.returncode == 0:
        print("[ INFO ] * Successfully deleted resource: " + deployment)
    else:
        print("[ ERROR ] * Failed to delete resource: " + deployment + "\n  Reason: " + output.stderr.decode())

for service in serviceNames:
    print("\n[ INFO ] Tearing down service: " + service)
    output = kubectlDeleteResourceCmd("service", service)
    if output.returncode == 0:
        print("[ INFO ] * Successfully deleted resource: " + service)
    else:
        print("[ ERROR ] * Failed to delete resource: " + service + "\n  Reason: " + output.stderr.decode())

for configmap in configmapNames:
    print("\n[ INFO ] Tearing down configmap: " + configmap)
    output = kubectlDeleteResourceCmd("configmap", configmap)
    if output.returncode == 0:
        print("[ INFO ] * Successfully deleted resource: " + configmap)
    else:
        print("[ ERROR ] * Failed to delete resource: " + configmap + "\n  Reason: " + output.stderr.decode())


print("\n\n-------- EXITED TEAR-DOWN PROCESS --------\n\n")