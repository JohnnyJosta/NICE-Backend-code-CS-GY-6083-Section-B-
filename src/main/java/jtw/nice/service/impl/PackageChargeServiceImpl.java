package jtw.nice.service.impl;

import jtw.nice.entity.PackageCharge;
import jtw.nice.entity.Package;
import jtw.nice.entity.dto.EmployeePackageDTO;
import jtw.nice.mapper.PackageChargeMapper;
import jtw.nice.mapper.PackageMapper;
import jtw.nice.service.PackageChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PackageChargeServiceImpl implements PackageChargeService {

    private final PackageChargeMapper packageChargeMapper;
    private final PackageMapper packageMapper;

    @Autowired
    public PackageChargeServiceImpl(PackageChargeMapper packageChargeMapper, PackageMapper packageMapper) {
        this.packageChargeMapper = packageChargeMapper;
        this.packageMapper = packageMapper;
    }

    @Override
    public int addPackageCharge(PackageCharge packageCharge) {
        packageChargeMapper.addPackageCharge(packageCharge);
        return packageCharge.getPackageChargeId();
    }

    @Override
    public void updatePackageCharge(int packageChargeId, PackageCharge packageCharge) {
        int rowsAffected = packageChargeMapper.updatePackageCharge(packageChargeId, packageCharge);
        if (rowsAffected == 0) {
            throw new RuntimeException("Failed to update PackageCharge with ID: " + packageChargeId);
        }
    }

    @Override
    public void deletePackageCharge(int packageChargeId) {
        int rowsAffected = packageChargeMapper.deletePackageCharge(packageChargeId);
        if (rowsAffected == 0) {
            throw new RuntimeException("Failed to delete PackageCharge with ID: " + packageChargeId);
        }
    }

    @Override
    public PackageCharge getPackageChargeById(int packageChargeId) {
        PackageCharge packageCharge = packageChargeMapper.getPackageChargeById(packageChargeId);
        if (packageCharge == null) {
            throw new RuntimeException("PackageCharge not found for ID: " + packageChargeId);
        }
        return packageCharge;
    }

    @Override
    public List<PackageCharge> getAllPackageCharges() {
        return packageChargeMapper.getAllPackageCharges();
    }

    @Override
    public List<EmployeePackageDTO> getEmployeePackageDTOs(int tripId, int groupId) {
        // Step 1: Fetch package charge data
        List<Map<String, Object>> packageCharges = packageChargeMapper.getPackageChargesByTripAndGroup(tripId, groupId);

        // Step 2: Convert package charge data to EmployeePackageDTO
        return packageCharges.stream().map(charge -> {
            Integer packageChargeId = (Integer) charge.get("packageChargeId");
            Integer packageId = (Integer) charge.get("packageId");
            // Convert java.sql.Date to java.time.LocalDate
            java.sql.Date sqlDate = (java.sql.Date) charge.get("chargeDate");
            LocalDate chargeDate = sqlDate.toLocalDate();

            // Step 3: Fetch package details from Package table
            Package pkg = packageMapper.getPackageById(packageId);
            String packageName = pkg.getPackageName();
            BigDecimal pricePerPerson = pkg.getPricePerPerson();

            // Step 4: Create DTO
            EmployeePackageDTO dto = new EmployeePackageDTO();
            dto.setPackageChargeId(packageChargeId);
            dto.setPackageName(packageName);
            dto.setChargeDate(chargeDate);
            dto.setPricePerPerson(pricePerPerson);

            return dto;
        }).collect(Collectors.toList());
    }
}
