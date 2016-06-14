package io.cloudslang.content.jclouds.entities;

/**
 * Created by Mihai Tusa.
 * 6/2/2016.
 */
public enum InstanceFilters {
    AFFINITY("affinity"),
    ARCHITECTURE("architecture"),
    AVAILABILITY_ZONE("availability-zone"),
    CLIENT_TOKEN("client-token"),
    DNS_NAME("dns-name"),
    GROUP_ID("group-id"),
    GROUP_NAME("group-name"),
    HOST_ID("host-Id"),
    HYPERVISOR("hypervisor"),
    IAM_INSTANCE_PROFILE_ARN("iam-instance-profile.arn"),
    IMAGE_ID("image-id"),
    INSTANCE_ID("instance-id"),
    INSTANCE_LIFECYCLE("instance-lifecycle"),
    INSTANCE_STATE_CODE("instance-state-code"),
    INSTANCE_STATE_NAME("instance-state-name"),
    INSTANCE_TYPE("instance-type"),
    INSTANCE_GROUP_ID("instance.group-id"),
    INSTANCE_GROUP_NAME("instance.group-name"),
    IP_ADDRESS("ip-address"),
    KERNEL_ID("kernel-id"),
    KEY_NAME("key-name"),
    LAUNCH_INDEX("launch-index"),
    LAUNCH_TIME("launch-time"),
    MONITORING_STATE("monitoring-state"),
    OWNER_ID("owner-id"),
    PLACEMENT_GROUP_NAME("placement-group-name"),
    PLATFORM("platform"),
    PRIVATE_DNS_NAME("private-dns-name"),
    PRIVATE_IP_ADDRESS("private-ip-address"),
    PRODUCT_CODE("product-code"),
    PRODUCT_CODE_TYPE("product-code.type"),
    RAMDISK_ID("ramdiskId"),
    REASON("reason"),
    REQUESTER_ID("requester-id"),
    RESERVATION_ID("reservation-id"),
    ROOT_DEVICE_NAME("root-device-name"),
    ROOT_DEVICE_TYPE("root-device-type"),
    SOURCE_DESTINATION_CHECK("source-dest-check"),
    SPOT_INSTANCE_REQUEST_ID("spot-instance-request-id"),
    STATE_REASON_CODE("state-reason-code"),
    STATE_REASON_MESSAGE("state-reason-message"),
    SUBNET_ID("subnet-id"),
    TENANCY("tenancy"),
    VIRTUALIZATION_TYPE("virtualization-type"),
    VPC_ID("vpc-id");

    private String value;

    InstanceFilters(String input) {
        this.value = input;
    }

    public String getValue() {
        return value;
    }
}