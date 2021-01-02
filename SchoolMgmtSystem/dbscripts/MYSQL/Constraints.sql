ALTER TABLE DistrictMaster ADD FOREIGN KEY (stateId) REFERENCES Statemaster(id);

ALTER TABLE StudentDetailMaster ADD FOREIGN KEY (`localStateId`) REFERENCES Statemaster(id);

ALTER TABLE StudentDetailMaster ADD FOREIGN KEY (`localDistrictId`) REFERENCES DistrictMaster(id);

ALTER TABLE StudentDetailMaster ADD FOREIGN KEY (`permanentStateId`) REFERENCES Statemaster(id);

ALTER TABLE StudentDetailMaster ADD FOREIGN KEY (`permanentDistrictId`) REFERENCES DistrictMaster(id);

ALTER TABLE StudentDetailMaster ADD FOREIGN KEY (`branchId`) REFERENCES SchoolBranch(branchId);

CREATE INDEX FOR_KEY_IDX ON StudentDetailMaster (`localStateId`, `localDistrictId`, `permanentDistrictId`, `permanentStateId`, `branchId`);