package de.htwg.caduserservice.model.requests;

import de.htwg.caduserservice.model.BillingModel;

public record RegisterTenantData(String displayName, BillingModel billingModel) {
}
