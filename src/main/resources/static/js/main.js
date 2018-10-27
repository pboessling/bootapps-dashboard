var APP = APP || (function () {

    var dashboardStatusUrl = '/api/dashboard/status';

    return {
        // TODO: Delete, if no longer needed.
        reloadHealth : function (element, url) {
            if(element && url) {
                console.debug('Reloading health from url: ' + url);

                fetch(url, {mode: 'no-cors'}).then(response => {
                    return response.json();
                }).then(data => {
                    console.log(data);
                }).catch(error => {
                    console.error(error);
                });
            }
        },

        // TODO: Delete, if no longer needed.
        reloadInfo : function (element, url) {
            if(element && url) {
                console.debug('Reloading info from url: ' + url);

                fetch(url, {mode: 'no-cors'}).then(response => {
                    return response.json();
                }).then(data => {
                    console.log(data);
                }).catch(error => {
                    console.error(error);
                });
            }
        },

        // TODO: Delete, if no longer needed.
        reloadAllEndpoints : function () {
            var bootapps = document.querySelectorAll('.bootapp');
            bootapps.forEach(function(bootapp) {
                var bootappHealth = bootapp.querySelector('.bootapp-health');
                var healthUrl = bootappHealth.getAttribute('data-bootapp-health-url');
                APP.reloadHealth(bootappHealth, healthUrl);

                var bootappInfo = bootapp.querySelector('.bootapp-info');
                var infoUrl = bootappInfo.getAttribute('data-bootapp-info-url');
                APP.reloadInfo(bootappInfo, infoUrl);
            });
        },

        /**
         * Reload status for a specific bootapp.
         * @param element the HTML element encapsulating the bootapp
         * @param bootappName the name of the bootapp
         */
        reloadStatus : function (element, bootappName) {
            if(element && bootappName) {
                console.debug('Reloading status for bootapp ' + bootappName);

                var url = dashboardStatusUrl + '/' + bootappName;
                fetch(url).then(response => {
                    return response.json();
                }).then(data => {
                    console.log(data);
                }).catch(error => {
                    console.error(error);
                });
            }
        },

        /**
         * Reload status for all bootapps.
         */
        reloadAllStatus : function () {
            fetch(dashboardStatusUrl).then(response => {
                return response.json();
            }).then(data => {
                for (var entry in data) {
                    var bootappStatus = data[entry];

                    // Update the bootapp status in the HTML.
                    var bootappElement = document.querySelector('#bootapp-' + bootappStatus.id);
                    bootappElement.querySelector('.bootapp-health').textContent = bootappStatus.health;
                    bootappElement.querySelector('.bootapp-info').textContent = bootappStatus.info;
                }
            }).catch(error => {
                console.error(error);
            });
        },

        enableAutoreload : function () {
            var autoreloadTimerId = setInterval(() => APP.reloadAllStatus(), autoreloadInterval);
        },

        /**
         * Initialize the application
         */
        init : function () {
            APP.reloadAllStatus();

            if(autoreload) {
                APP.enableAutoreload();
            }
        }
    }

})();

APP.init();