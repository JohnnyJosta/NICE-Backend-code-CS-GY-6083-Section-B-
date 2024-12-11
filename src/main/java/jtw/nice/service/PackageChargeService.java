package jtw.nice.service;

import jtw.nice.entity.PackageCharge;
import jtw.nice.entity.dto.EmployeePackageDTO;
import jtw.nice.entity.dto.EmployeeRoomDTO;

import java.util.List;

public interface PackageChargeService {

    /**
     * Add a new PackageCharge.
     *
     * @param packageCharge The PackageCharge entity to add.
     * @return The ID of the newly created PackageCharge.
     */
    int addPackageCharge(PackageCharge packageCharge);

    /**
     * Update an existing PackageCharge.
     *
     * @param packageChargeId The ID of the PackageCharge to update.
     * @param packageCharge The PackageCharge entity with updated values.
     */
    void updatePackageCharge(int packageChargeId, PackageCharge packageCharge);

    /**
     * Delete a PackageCharge by ID.
     *
     * @param packageChargeId The ID of the PackageCharge to delete.
     */
    void deletePackageCharge(int packageChargeId);

    /**
     * Get a PackageCharge by ID.
     *
     * @param packageChargeId The ID of the PackageCharge.
     * @return The PackageCharge entity.
     */
    PackageCharge getPackageChargeById(int packageChargeId);

    /**
     * Get all PackageCharges.
     *
     * @return A list of all PackageCharge entities.
     */
    List<PackageCharge> getAllPackageCharges();

    List<EmployeePackageDTO> getEmployeePackageDTOs(int tripId, int groupId);
}
