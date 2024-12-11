package jtw.nice.service;

import jtw.nice.entity.Package;

import java.util.List;

public interface PackageService {
    int addPackage(Package pkg);

    void updatePackage(int id, Package pkg);

    void deletePackage(int id);

    Package getPackageById(int id);

    List<Package> getAllPackages();
}
