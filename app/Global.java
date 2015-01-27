import play.*;
import play.mvc.*;
import play.mvc.Http.*;
import play.libs.F.*;
import play.api.mvc.EssentialFilter;
import play.filters.csrf.CSRFFilter;

/**
 * @author nikos
 *
 */
public class Global extends GlobalSettings {

  @Override
  public void onStart(Application app) {
    Logger.info("play-test Application has started");
  }

  @Override
  public void onStop(Application app) {
    Logger.info("play-test Application shutdown...");
  }

  @Override
  public Promise<Result> onError(RequestHeader request, Throwable t) {
    // return Promise.<Result>pure(internalServerError(views.html.errorPage.render(t)));
    return super.onError(request, t);
  }


  @Override
  @SuppressWarnings("unchecked")
  public <T extends EssentialFilter> Class<T>[] filters() {
    return new Class[] {CSRFFilter.class};
    // return new Class[]{GzipFilter.class};
  }

}
