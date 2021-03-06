import $ from 'jquery';
import React from 'react';
import Main from './main';
import '../../helpers/handlebars-helpers';

function requestPermissionTemplates () {
  return $.get(baseUrl + '/api/permissions/search_templates');
}

window.sonarqube.appStarted.then(options => {
  requestPermissionTemplates().done(r => {
    var el = document.querySelector(options.el);
    React.render(<Main permissionTemplates={r.permissionTemplates} componentId={options.componentId}/>, el);
  });
});
