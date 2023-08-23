package <PROJECT_NAME>;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.resourcemanager.Azure;
import com.azure.resourcemanager.compute.models.VirtualMachine;
import com.azure.resourcemanager.compute.models.VirtualMachineSizeTypes;
import com.azure.resourcemanager.compute.models.KnownLinuxVirtualMachineImage;
import com.azure.resourcemanager.compute.models.VirtualMachineCustomImage;

public class AzureVMCreator {

    public static void main(String[] args) {
        String clientId = "<your-client-id>";
        String tenantId = "<your-tenant-id>";
        String subscriptionId = "<your-subscription-id>";
        String resourceGroupName = "<your-resource-group-name>";
        String vmName = "<your-vm-name>";

        TokenCredential credential = new DefaultAzureCredentialBuilder()
                .clientId(clientId)
                .tenantId(tenantId)
                .build();

        Azure azure = Azure.configure()
                .authenticate(credential)
                .withSubscription(subscriptionId);

        VirtualMachine virtualMachine = azure.virtualMachines().define(vmName)
                .withRegion("East US")
                .withExistingResourceGroup(resourceGroupName)
                .withNewPrimaryNetwork("10.0.0.0/24")
                .withPrimaryPrivateIPAddressDynamic()
                .withNewPrimaryPublicIPAddress(vmName + "ip")
                .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_18_04_LTS)
                .withRootUsername("azureuser")
                .withSsh("<your-ssh-public-key>")
                .withSize(VirtualMachineSizeTypes.STANDARD_DS2_V2)
                .create();

        System.out.println("Virtual machine created: " + virtualMachine.id());
    }
}
