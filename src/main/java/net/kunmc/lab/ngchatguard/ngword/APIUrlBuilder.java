package net.kunmc.lab.ngchatguard.ngword;

import java.net.MalformedURLException;
import java.net.URL;
import net.kunmc.lab.ngchatguard.Store;

class APIUrlBuilder {

  static URL build() throws MalformedURLException {
    return new URL(
        "https://script.google.com/macros/s/"
            .concat(Store.config.id.value())
            .concat("/exec?token=")
            .concat(Store.config.token.value()));
  }
}
