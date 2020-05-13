package com.cx.restclient.sca.dto.report;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PackageUsage implements Serializable {
    public String usageType;
    public String packageId;
}
