'''
the format is using str.format see https://docs.python.org/3/library/stdtypes.html#str.format
'''


# import the logging library
import logging

# Get an instance of a logger
logger = logging.getLogger('app')


def debug(msg, *args, **kwargs):
    logger.debug(msg.format(*args, **kwargs))


def info(msg, *args, **kwargs):
    logger.info(msg.format(*args, **kwargs))


def warn(msg, *args, **kwargs):
    logger.warn(msg.format(*args, **kwargs))


def error(msg, *args, **kwargs):
    logger.error(msg.format(*args, **kwargs))


def exception(msg, *args, **kwargs):
    logger.exception(msg.format(*args, **kwargs))
