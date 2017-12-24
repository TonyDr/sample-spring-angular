app.controller('staffController', function ($scope, $http) {
    $scope.headingTitle = "Staff List";
    $scope.staffForm = {
        id : "",
        name: "",
        role: ""
    };

    getStaffList();

    function getStaffList() {
        $http.get("/rest/staff/list")
            .then(function (response) {
                $scope.staffList = response.data;
            });
    }


    $scope.processStaff = function () {
        var actionUrl;
        if (!$scope.staffForm.id) {
            actionUrl = '/rest/staff/create';
        } else {
            actionUrl = '/rest/staff/update'
        }
        $http({
            method: 'POST',
            url: actionUrl,
            data: angular.toJson($scope.staffForm),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(getStaffList(), clearForm())
            .success(function (data) {
                $scope.staffList = data
            });
    };

    $scope.editStaff = function (staff) {
        $scope.staffForm.id = staff.id;
        $scope.staffForm.name = staff.name;
        $scope.staffForm.role = staff.role;
        disableName();
    };
    $scope.deleteStaff = function (staff) {
        $http({
            method: 'POST',
            url: '/rest/staff/delete',
            data: angular.toJson(staff),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(getStaffList());
    };

    function clearForm() {
        $scope.staffForm.name = "";
        $scope.staffForm.id = "";
        document.getElementById("name").disabled = false;
    }

    function disableName() {
        document.getElementById("name").disabled = true;
    }
});


app.controller('auditController', function ($scope, $http) {
    $scope.headingTitle = "Audit List";
    $http.get("/rest/audit/list")
        .then(function (response) {
            $scope.audit = response.data;
        });
});