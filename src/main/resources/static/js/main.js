var APP = APP || (function () {

    var dashboardStatusUrl = '/api/status';
    var autoreloadTimerId;

    return {
        /**
         * Reload status for a specific bootapp.
         * @param element the HTML element encapsulating the bootapp
         * @param reloadId the reload id, pattern: <environmentId>:<hostId>:<bootappId>
         */
        reloadStatus : function (element, reloadId) {
            if(element && reloadId) {
                console.debug('Reloading status for bootapp ' + reloadId);

                var reloadIdParts = reloadId.split(':');
                var environmentId = reloadIdParts[0];
                var hostId = reloadIdParts[1];
                var bootappId = reloadIdParts[2];

                fetch(dashboardStatusUrl + '/' + environmentId + '/' + hostId + '/' + bootappId).then(response => {
                    return response.json();
                }).then(data => {
                    console.debug(data);
                    APP.updateBootappStatusHTML(hostId, data);
                }).catch(error => {
                    console.error(error);
                });
            }
        },

        /**
         * Reload status for all bootapps.
         */
        reloadAllStatus : function () {
            console.debug('Reloading all bootapp statuses');

            fetch(dashboardStatusUrl + '/' + environmentId).then(response => {
                return response.json();
            }).then(data => {
                console.debug(data);
                for (var host in data) {
                    for (var bootapp in data[host]) {
                        APP.updateBootappStatusHTML(host, data[host][bootapp]);
                    }
                }
            }).catch(error => {
                console.error(error);
            });
        },

        /**
         * Updates the bootapp status in the HTML.
         * @param host the host of the bootapp
         * @param bootappStatus the bootapp status
         */
        updateBootappStatusHTML : function (host, bootappStatus) {
            var bootappElement = document.querySelector('#bootapp-' + host + '-' + bootappStatus.id);

            var bootappHealth = bootappStatus.health;
            var bootappHealthElement = bootappElement.querySelectorAll('.bootapp-health span')[1];
            bootappHealthElement.textContent = bootappHealth;
            if(bootappHealth === 'UP') {
                bootappHealthElement.className = 'tag is-success';
            } else  {
                bootappHealthElement.className = 'tag is-danger';
            }

            bootappElement.querySelector('.bootapp-info').textContent = bootappStatus.info;
        },

        /**
         * Enabled the autoreload of the bootapp statuses.
         */
        enableAutoreload : function () {
            autoreloadTimerId = setInterval(() => APP.reloadAllStatus(), autoreloadInterval);
        },

        /**
         * Disables the autoreload of the bootapp statuses.
         */
        disableAutoreload : function () {
            clearInterval(autoreloadTimerId);
        },

        /**
         * Registers the reload status buttons.
         */
        registerReloadStatusButtons : function () {
            document.querySelector('#bootapps-reload-status-button').addEventListener('click', APP.reloadAllStatus);

            document.querySelectorAll('.bootapp-reload-status-button').forEach(function(button) {
                button.addEventListener('click', event => {
                    APP.reloadStatus(event.srcElement, event.srcElement.dataset.reloadId)
                });
            });
        },

        /**
         * Registers the autoreload checkbox.
         */
        registerAutoreloadCheckbox : function () {
            document.querySelector('#bootapps-autoreload-checkbox').addEventListener('change', function() {
                if (this.checked) {
                    APP.enableAutoreload();
                } else {
                    APP.disableAutoreload();
                }
            });
        },

        /**
         * Initializes the application
         */
        init : function () {
            APP.reloadAllStatus();
            APP.registerAutoreloadCheckbox();
            APP.registerReloadStatusButtons();

            if(autoreload) {
                APP.enableAutoreload();
            }
        }
    }

})();

APP.init();