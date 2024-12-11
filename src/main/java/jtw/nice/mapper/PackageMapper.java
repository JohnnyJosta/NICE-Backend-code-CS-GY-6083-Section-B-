package jtw.nice.mapper;

import jtw.nice.entity.Package;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PackageMapper {
    int addPackage(Package pkg);

    int updatePackage(@Param("id") int id, @Param("pkg") Package pkg);

    int deletePackage(int packageId);

    Package getPackageById(int packageId);

    List<Package> getAllPackages();
}
