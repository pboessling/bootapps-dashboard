var APP = APP || (function () {

    var dashboardStatusUrl = '/api/dashboard/status';
    var autoreloadTimerId;

    return {
        /**
         * Reload status for a specific bootapp.
         * @param element the HTML element encapsulating the bootapp
         * @param bootappId the name of the bootapp
         */
        reloadStatus : function (element, bootappId) {
            if(element && bootappId) {
                console.debug('Reloading status for bootapp ' + bootappId);

                fetch(dashboardStatusUrl + '/' + bootappId).then(response => {
                    return response.json();
                }).then(data => {
                    console.debug(data);
                    APP.updateBootappStatusHTML(data);
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

            fetch(dashboardStatusUrl).then(response => {
                return response.json();
            }).then(data => {
                console.debug(data);
                for (var entry in data) {
                    APP.updateBootappStatusHTML(data[entry]);
                }
            }).catch(error => {
                console.error(error);
            });
        },

        /**
         * Updates the bootapp status in the HTML.
         * @param bootappStatus the bootapp status
         */
        updateBootappStatusHTML : function (bootappStatus) {
            var bootappElement = document.querySelector('#bootapp-' + bootappStatus.id);

            var bootappHealth = bootappStatus.health;
            bootappElement.querySelector('.bootapp-health').textContent = bootappHealth;
            if(bootappHealth === 'UP') {
                bootappElement.style.backgroundColor = 'green';
            } else  {
                bootappElement.style.backgroundColor = 'red';
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
                    APP.reloadStatus(event.srcElement, event.srcElement.dataset.bootappId)
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