## CropSense ecosystem kubernetes deployment automation script

## Imported modules
import subprocess
import sys
import time

## --- Helper functions ---

# Apply a yaml file containing k8s API payload
def kubectlApplyCmd(filePath, yamlFile):
    applyCmd = "kubectl apply -f " + filePath + "/" + yamlFile
    print("[ INFO ] Running command: " + applyCmd)
    output = subprocess.run(applyCmd, shell=True, capture_output=True)
    return output

## --- Deployment process ---

print("\n\n-------- ENTERING DEPLOYMENT PROCESS --------\n\n")

## Common file names
k8sResources = { 
    "deployment" : "deployment.yaml",
    "service" : "service.yaml",
    "configmap" : "configmap.yaml"
}

# file path for app-server
appServerFilePath = "./app_server"
print("[ INFO ] File path for application-server kubernetes resources: " + appServerFilePath)
# file path for metrics-server
metricsServerFilePath = "./metrics_server"
print("[ INFO ] File path for metrics-server kubernetes resources: " + metricsServerFilePath)

components = ["app-server", "metrics-server"]
componentCount = len(components)
iter = 0
for service in components:
    iter = iter + 1
    print("\n\n[ INFO ] Deploying component: " + service + "\n")
    
    currentFilePath = None
    if service == "app-server":
        currentFilePath = appServerFilePath
    elif service == "metrics-server":
        currentFilePath = metricsServerFilePath

    # deploy the configmap resource
    output = kubectlApplyCmd(currentFilePath, k8sResources.get("configmap"))
    if output.returncode == 0:
        print("[ INFO ] * Successfully created the " + service + " configmap resource\n")
    else:
        print("[ ERROR ] * Failed to create the configmap resource for " + service + "\nwith reason: " + output.stdout.decode())
        sys.exit()

    # deploy the service resource
    output = kubectlApplyCmd(currentFilePath, k8sResources.get("service"))
    if output.returncode == 0:
        print("[ INFO ] * successfully created the " + service + " service resource\n")
    else:
        print("[ ERROR ] * Failed to create the service resource for " + service + "\nwith reason: " + output.stderr.decode())
        sys.exit()

    # deploy the deployment resource
    output = kubectlApplyCmd(currentFilePath, k8sResources.get("deployment"))
    if output.returncode == 0:
        print("[ INFO ] * Successfully created the " + service + " deployment resource\n")
    else:
        print("[ ERROR ] * Failed to create the deployment resource for " + service + "\n   with reason: " + output.stderr.decode())
        sys.exit()

    if iter == componentCount:
        break

    # sleep for 10 seconds
    print("[ INFO ] sleeping for 10 seconds before moving on to the next component")
    time.sleep(10)

print("\n\n[ INFO ] Completed the deployment process for all services")
    
print("\n\n-------- EXITED DEPLOYMENT PROCESS --------\n\n")