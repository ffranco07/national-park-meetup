package com.cti.npmeetup.logback;

import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;
import ch.qos.logback.core.pattern.color.ANSIConstants;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.Level;

/**
 * Highlighting composite converter module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

public class HighlightingCompositeConverterEx extends ForegroundCompositeConverterBase<ILoggingEvent> {
	
	@Override
	protected String getForegroundColorCode(ILoggingEvent event) {
		Level level = event.getLevel();
		switch (level.toInt()) {
		case Level.ERROR_INT:
			return ANSIConstants.BOLD + ANSIConstants.RED_FG; // same as default color scheme
		case Level.WARN_INT:
			return ANSIConstants.YELLOW_FG;
		case Level.INFO_INT:
			return ANSIConstants.GREEN_FG;
		case Level.DEBUG_INT:
			return ANSIConstants.CYAN_FG;
		case Level.TRACE_INT:
			return ANSIConstants.MAGENTA_FG;
		default:
			return ANSIConstants.WHITE_FG;
		}
	}
	
}
