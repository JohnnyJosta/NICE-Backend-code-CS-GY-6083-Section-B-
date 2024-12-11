package jtw.nice.service.impl;

import jtw.nice.entity.Package;
import jtw.nice.mapper.PackageMapper;
import jtw.nice.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageServiceImpl implements PackageService {

    private final PackageMapper packageMapper;

    @Autowired
    public PackageServiceImpl(PackageMapper packageMapper) {
        this.packageMapper = packageMapper;
    }

    @Override
    public int addPackage(Package pkg) {
        packageMapper.addPackage(pkg);
        return pkg.getPackageId();
    }

    @Override
    public void updatePackage(int id, Package pkg) {
        int rows = packageMapper.updatePackage(id, pkg);
        if (rows == 0) {
            throw new RuntimeException("Failed to update package with ID: " + id);
        }
    }

    @Override
    public void deletePackage(int id) {
        int rows = packageMapper.deletePackage(id);
        if (rows == 0) {
            throw new RuntimeException("Failed to delete package with ID: " + id);
        }
    }

    @Override
    public Package getPackageById(int id) {
        Package pkg = packageMapper.getPackageById(id);
        if (pkg == null) {
            throw new RuntimeException("Package not found for ID: " + id);
        }
        return pkg;
    }

    @Override
    public List<Package> getAllPackages() {
        return packageMapper.getAllPackages();
    }
}
