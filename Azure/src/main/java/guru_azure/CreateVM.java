package <PROJECT_NAME>;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.resourcemanager.AzureResourceManager;
import com.azure.resourcemanager.compute.models.KnownLinuxVirtualMachineImage;
import com.azure.resourcemanager.compute.models.VirtualMachine;
import com.azure.resourcemanager.compute.models.VirtualMachineSizeTypes;
import com.azure.resourcemanager.network.models.Network;
import com.azure.resourcemanager.network.models.PublicIPAddress;
import com.azure.resourcemanager.network.models.Subnet;
import com.azure.resourcemanager.resources.fluentcore.arm.Region;
import com.azure.resourcemanager.resources.fluentcore.utils.ResourceNamer;

public class CreateVM {

    public static void main(String[] args) {
        // Define your Azure credentials
        TokenCredential credential = new DefaultAzureCredentialBuilder().build();

        // Create an Azure Resource Manager instance
        AzureResourceManager azure = AzureResourceManager.configure()
                .authenticate(credential)
                .withDefaultSubscription();

        // Define the resource group and VM details
        String resourceGroupName = "myResourceGroup";
        String vmName = "myvm";
        Region region = Region.US_WEST;

        // Define network details
        String vnetName = ResourceNamer.randomResourceName("vnet", 8);
        String subnetName = ResourceNamer.randomResourceName("subnet", 8);
        String publicIPName = ResourceNamer.randomResourceName("pip", 8);

        // Create a virtual network
        Network network = azure.networks().define(vnetName)
                .withRegion(region)
                .withNewResourceGroup(resourceGroupName)
                .withAddressSpace("10.0.0.0/16")
                .withSubnet(subnetName, "10.0.0.0/24")
                .create();

        // Create a public IP address
        PublicIPAddress publicIPAddress = azure.publicIPAddresses().define(publicIPName)
                .withRegion(region)
                .withExistingResourceGroup(resourceGroupName)
                .create();

        // Create the virtual machine
        VirtualMachine virtualMachine = azure.virtualMachines().define(vmName)
                .withRegion(region)
                .withExistingResourceGroup(resourceGroupName)
                .withExistingPrimaryNetwork(network)
                .withSubnet(subnetName)
                .withPrimaryPrivateIPAddressDynamic()
                .withExistingPrimaryPublicIPAddress(publicIPAddress)
                .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_18_04_LTS)
                .withRootUsername("adminUser")
                .withSsh("sshKey")
                .withSize(VirtualMachineSizeTypes.STANDARD_DS2_V2)
                .create();

        System.out.println("Virtual Machine created: " + virtualMachine.id());
    }
}
