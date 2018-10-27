var APP = APP || (function () {

    return {
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

                //https://stackoverflow.com/questions/43317967/handle-response-syntaxerror-unexpected-end-of-input-when-using-mode-no-cors/43319482
            }
        },

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

        init : function () {
            APP.reloadAllEndpoints();
        }
    }

})();

APP.init();