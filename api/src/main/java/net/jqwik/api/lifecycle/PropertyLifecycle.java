package net.jqwik.api.lifecycle;

import org.apiguardian.api.*;

import net.jqwik.api.*;

import static org.apiguardian.api.API.Status.*;

/**
 * Experimental feature. Not ready for public usage yet.
 */
@API(status = EXPERIMENTAL, since = "1.2.3")
public class PropertyLifecycle {

	// OLD stuff below shall be removed

	@FunctionalInterface
	public interface AfterPropertyExecutor {
		PropertyExecutionResult execute(PropertyExecutionResult executionResult, PropertyLifecycleContext context);
	}

	@API(status = INTERNAL)
	public static abstract class PropertyLifecycleFacade {
		private static PropertyLifecycle.PropertyLifecycleFacade implementation;

		static {
			implementation = FacadeLoader.load(PropertyLifecycle.PropertyLifecycleFacade.class);
		}

		public abstract void after(Object key, AfterPropertyExecutor afterPropertyExecutor);
	}

	private static void onSuccess(Object identifier, Runnable runnable) {
		AfterPropertyExecutor afterPropertyExecutor = (executionResult, context) -> {
			if (executionResult.status() == PropertyExecutionResult.Status.SUCCESSFUL) {
				runnable.run();
			}
			return executionResult;
		};
		after(Tuple.of(identifier, runnable.getClass()), afterPropertyExecutor);
	}

	public static void onSuccess(Runnable runnable) {
		onSuccess(runnable.getClass(), runnable);
	}

	private static void after(Object identifier, AfterPropertyExecutor afterPropertyExecutor) {
		PropertyLifecycleFacade.implementation.after(identifier, afterPropertyExecutor);
	}

}
