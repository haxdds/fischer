BUILD_DIR = out/production
APP_NAME = Chess
OUT_DIR = $(BUILD_DIR)/$(APP_NAME)/
PROJECT_DIR = src
MAIN_FILE = chess/Test
JAVA_FILES = $(find $(PROJECT_DIR) -name '*.java')
all:
	make setup
	make compile
	make run

setup:
	if ! [ -d $(OUT_DIR) ]; then mkdir -p $(OUT_DIR); fi

compile: $(JAVA_FILES)
	javac -d $(OUT_DIR) $$(find $(PROJECT_DIR) -name '*.java')

run:
	java -cp $(OUT_DIR) $(MAIN_FILE)