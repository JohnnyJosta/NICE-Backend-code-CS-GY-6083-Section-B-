package jtw.nice.mapper;

import jtw.nice.entity.PackageCharge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PackageChargeMapper {

    /**
     * Add a new PackageCharge.
     *
     * @param packageCharge The PackageCharge entity to add.
     * @return The number of rows affected.
     */
    int addPackageCharge(PackageCharge packageCharge);

    /**
     * Update an existing PackageCharge.
     *
     * @param packageChargeId The ID of the PackageCharge to update.
     * @param packageCharge The PackageCharge entity with updated values.
     * @return The number of rows affected.
     */
    int updatePackageCharge(@Param("packageChargeId") int packageChargeId, @Param("packageCharge") PackageCharge packageCharge);

    /**
     * Delete a PackageCharge by ID.
     *
     * @param packageChargeId The ID of the PackageCharge to delete.
     * @return The number of rows affected.
     */
    int deletePackageCharge(int packageChargeId);

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

    /**
     * Get all package charges for a specific trip and group.
     *
     * @param tripId The trip ID.
     * @param groupId The group ID.
     * @return A list of package charges.
     */
    List<Map<String, Object>> getPackageChargesByTripAndGroupId(@Param("tripId") int tripId, @Param("groupId") int groupId);

    List<Map<String, Object>> getPackageChargesByTripAndGroup(@Param("tripId") int tripId, @Param("groupId") int groupId);
}
