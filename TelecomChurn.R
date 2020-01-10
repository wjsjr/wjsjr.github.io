#For UVA's Comm 4559: Business Analytics with R. This was final project for course. 
#Used Telecom Churn Dataset from Kagglehttps://www.kaggle.com/dileep070/logisticregression-telecomcustomer-churmprediction 


# ---------
# Read Data
# ---------

setwd("~/Desktop/COMM 4559/Project")

library(readxl)
library(dplyr)
library(lubridate)
library(caret)
library(quanteda)
library(textclean)
library(tibble)
library(parallel)
library(doParallel)
library(stringr)
library(janitor)
library(rpart)
library(pROC)
library(car)
library(effects)
library(data.table)
library(scales)
library(gbm)
library(rminer)


churn <- read.csv("churn_data.csv")
cust <- read.csv("customer_data.csv")
net <- read.csv("internet_data.csv")


head(churn)
str(churn)
dim(churn)

head(cust)
str(cust)
dim(cust)

head(net)
str(net)
dim(net)


length(which(is.na(churn)))
length(which(is.na(cust)))
length(which(is.na(net)))

dat <- merge(x=churn, y=cust, by="customerID")
dat <- merge(x=dat, y=net, by = "customerID")

setnames(dat, old=c("tenure","gender"), new=c("Tenure", "Gender"))
dat$SeniorCitizen <- as.factor(ifelse(dat$SeniorCitizen == 1, "Yes", "No"))

percent((nrow(dat) - nrow(na.omit(dat)))/nrow(dat))
dat <- na.omit(dat)

head(dat)
str(dat)
dim(dat)

dat <- subset(dat, select = -(customerID))

set.seed(777)


table(dat$Churn)


trainIndex <- createDataPartition(dat$Churn, p = 0.8, list = FALSE, times = 1)
datTrain <- dat[trainIndex,]
datTest <- dat[-trainIndex,]

table(dat$Churn)

datTrainDS <- downSample(datTrain, datTrain$Churn, list = TRUE)$x
table(datTrainDS$Churn)


dat.preProc <- preProcess(datTrainDS, method = c("zv")) 
datTrain.processed <- predict(dat.preProc, datTrain)
datTest.processed <- predict(dat.preProc, datTest) 


dummy.preProc <- dummyVars( ~ ., data = datTrain.processed %>% select(-Churn), fullRank = FALSE)
datTrain.clean <- predict(dummy.preProc, datTrain.processed) %>%
  as_tibble() %>%
  mutate(Churn = datTrain.processed$Churn) %>%
  select(Churn, everything())

datTest.clean <- predict(dummy.preProc, datTest.processed) %>%
  as_tibble() %>%
  mutate(Churn = datTest.processed$Churn) %>%
  select(Churn, everything())


ctrl <- trainControl(method = "cv", number = 5)
c5ctrl <- trainControl(method = "repeatedcv", number = 5, repeats = 2)
ctrlRanger <- trainControl(method = "cv", number = 5, classProbs = TRUE)


tuneGrid.rpart <- expand.grid(
  cp = c(.01, .03, .05)
)

tuneGrid.gbm <- expand.grid(
  interaction.depth = c(1, 3, 5),
  n.trees = seq(100, 500, 100),
  shrinkage = c(.01, .1, .3),
  n.minobsinnode = c(5, 10, 15)
)

tuneGrid.C5.0 <-  expand.grid(
  winnow = TRUE,
  trials = 100,
  model = c("tree", "rules")
)

tuneGrid.rf <-  expand.grid(
  mtry = floor(sqrt(ncol(subset(datTrain.clean, select = -c(Churn)))))
)

tuneGrid.ranger <-  expand.grid(
  mtry = floor(sqrt(ncol(subset(datTrain.clean, select = -c(Churn))))),
  splitrule = "gini",
  min.node.size = 1
)


cl <- makeCluster(detectCores())
registerDoParallel(cl)

# rpart

start <- proc.time()

dat.train.rpart <- train(
  y = datTrain.clean$Churn,
  x = subset(datTrain.clean, select = -c(Churn)),
  method = "rpart",
  trControl = ctrl,
  tuneGrid = tuneGrid.rpart,
  na.action = na.pass)

(rpart.runTime <- proc.time() - start)

# gbm

start <- proc.time() 

dat.train.gbm <- train(
  y = datTrain.clean$Churn, 
  x = subset(datTrain.clean, select = -c(Churn)), 
  method = "gbm", 
  trControl = ctrl, 
  tuneGrid = tuneGrid.gbm, 
  verbose = FALSE)

(gbm.runTime <- proc.time() - start)

# C5.0

start <- proc.time() 

dat.train.C5.0 <- train(
  y = datTrain.clean$Churn, 
  x = subset(datTrain.clean, select = -c(Churn)), 
  method = "C5.0", 
  trControl = c5ctrl, 
  tuneGrid = tuneGrid.C5.0,
  na.action = na.pass)

(C5.0.runTime <- proc.time() - start)

# SVM

start <- proc.time() 

dat.train.svm <- train(
  y = datTrain.clean$Churn, 
  x = subset(datTrain.clean, select = -c(Churn)), 
  method = "svmLinear2", 
  trControl = ctrl,
  probability = TRUE)

(svm.runTime <- proc.time() - start)

# rf

start <- proc.time() 

dat.train.rf <- train(
  y = datTrain.clean$Churn, 
  x = subset(datTrain.clean, select = -c(Churn)),  
  method = "rf", 
  trControl = ctrl, 
  tuneGrid = tuneGrid.rf,
  ntree = 250, 
  na.action = na.pass)

(rf.runTime <- proc.time() - start)

# ranger

start <- proc.time() 

dat.train.ranger <- train(
  y = datTrain.clean$Churn, 
  x = subset(datTrain.clean, select = -c(Churn)),
  method = "ranger", 
  trControl = ctrlRanger, 
  tuneGrid = tuneGrid.ranger,
  importance = "impurity",
  num.trees = 500)

(ranger.runTime <- proc.time() - start)

stopCluster(cl)



# ------------------------------------
# Evaluate Predictive Analysis Results
# ------------------------------------


plot(varImp(dat.train.rpart, scale = FALSE), 20)
plot(varImp(dat.train.gbm, scale = FALSE), 20)
plot(varImp(dat.train.C5.0, metric = "splits", scale = FALSE), 20)
plot(varImp(dat.train.rf, scale = FALSE), 20)
plot(varImp(dat.train.ranger, scale = FALSE), 20)

Predictions.rpart <- predict(dat.train.rpart, newdata = datTest.clean)
Predictions.gbm <- predict(dat.train.gbm, newdata = datTest.clean)
Predictions.C5.0 <- predict(dat.train.C5.0, newdata = datTest.clean)
Predictions.svm <- predict(dat.train.svm, newdata = datTest.clean)
Predictions.rf <- predict(dat.train.rf, newdata = datTest.clean)
Predictions.ranger <- predict(dat.train.ranger, newdata = datTest.clean)

rpart.CM <- confusionMatrix(data = Predictions.rpart, reference = datTest$Churn, positive = "No")
gbm.CM <- confusionMatrix(data = Predictions.gbm, reference = datTest$Churn, positive = "No")
C5.0.CM <- confusionMatrix(data = Predictions.C5.0, reference = datTest$Churn, positive = "No")
svm.CM <- confusionMatrix(data = Predictions.svm, reference = datTest$Churn, positive = "No")
rf.CM <- confusionMatrix(data = Predictions.rf, reference = datTest$Churn, positive = "No")
ranger.CM <- confusionMatrix(data = Predictions.ranger, reference = datTest$Churn, positive = "No")

rpart.CM
gbm.CM
C5.0.CM
svm.CM
rf.CM
ranger.CM


PredictionProbs.rpart <- predict(dat.train.rpart, newdata = datTest.clean, type = "prob")
PredictionProbs.gbm <- predict(dat.train.gbm, newdata = datTest.clean, type = "prob")
PredictionProbs.C5.0 <- predict(dat.train.C5.0, newdata = datTest.clean, type = "prob")
PredictionProbs.svm <- predict(dat.train.svm, newdata = datTest.clean, type = "prob")
PredictionProbs.rf <- predict(dat.train.rf, newdata = datTest.clean, type = "prob")
PredictionProbs.ranger <- predict(dat.train.ranger, newdata = datTest.clean, type = "prob")


rpart.ROC <- roc(
  predictor = PredictionProbs.rpart[, 2],
  response = datTest$Churn,
  levels = levels(datTest$Churn))

gbm.ROC <- roc(
  predictor = PredictionProbs.gbm[, 2],
  response = datTest$Churn,
  levels = levels(datTest$Churn))

C5.0.ROC <- roc(
  predictor = PredictionProbs.C5.0[, 2],
  response = datTest$Churn,
  levels = levels(datTest$Churn))

svm.ROC <- roc(
  predictor = PredictionProbs.svm[, 2],
  response = datTest$Churn,
  levels = levels(datTest$Churn))

rf.ROC <- roc(
  predictor = PredictionProbs.rf[, 2],
  response = datTest$Churn,
  levels = levels(datTest$Churn))

ranger.ROC <- roc(
  predictor = PredictionProbs.ranger[, 2],
  response = datTest$Churn,
  levels = levels(datTest$Churn))


auc(rpart.ROC)
auc(gbm.ROC)
auc(C5.0.ROC)
auc(svm.ROC)
auc(rf.ROC)
auc(ranger.ROC)


AUCs <- c(auc(rpart.ROC), auc(gbm.ROC), auc(C5.0.ROC), auc(svm.ROC), auc(rf.ROC), auc(ranger.ROC))
bestOrder <- order(AUCs, decreasing = TRUE)
models <- paste0(c("rpart", "gbm", "C5.0", "svm", "rf", "ranger"), " (", round(AUCs, 2), ")")
ggroc(list(rpart.ROC, gbm.ROC, C5.0.ROC, svm.ROC, rf.ROC, ranger.ROC)[bestOrder], size = 1) + 
  geom_abline(intercept = 1, slope = 1, size = 2, alpha = .2, linetype = 2) + 
  scale_color_manual(values = c("purple", "red", "green", "blue", "orange", "black"),
                     labels = models[bestOrder]) + 
  theme_bw(base_size = 16) +
  labs(title = "Test Data ROCs",
       color = "Model (AUC)",
       x = "Specificity",
       y = "Sensitivity")


# kappa and accuracy
compareModels <- resamples(list(rpart = dat.train.rpart,
                                gbm = dat.train.gbm, 
                                C5.0 = dat.train.C5.0,
                                svm = dat.train.svm,
                                rf = dat.train.rf,
                                ranger = dat.train.ranger))
dotplot(compareModels)


# run times
runTimes <- tibble(
  model = c("rpart", "gbm", "C5.0", "svm", "rf", "ranger"),
  runtime = c(rpart.runTime["elapsed"], 
              gbm.runTime["elapsed"], 
              C5.0.runTime["elapsed"], 
              svm.runTime["elapsed"], 
              rf.runTime["elapsed"], 
              ranger.runTime["elapsed"]))
ggplot(runTimes, aes(x = reorder(model, -runtime), y = runtime, fill = factor(runtime))) + 
  geom_bar(stat = "identity") +
  theme_bw(base_size = 16) + 
  labs(title = "Model Fit Time",
       caption = "Lower is better",
       y = "Time (Seconds)",
       x = "Model") +
  theme(legend.position = "none")


# balanced accuracy considering run time
testComparison <- bind_rows(rpart.CM$byClass,
                            gbm.CM$byClass,
                            C5.0.CM$byClass,
                            svm.CM$byClass,
                            rf.CM$byClass,
                            ranger.CM$byClass) %>%
  mutate(model = c("rpart", "gbm", "C5.0", "svm", "rf", "ranger")) %>%
  select(model, everything())
testComparison %>%
  select(model, `Balanced Accuracy`) %>%
  arrange(desc(`Balanced Accuracy`))
testComparisonWithRuntimes <- testComparison %>%
  left_join(runTimes)
ggplot(testComparisonWithRuntimes, aes(x = reorder(model, -`Balanced Accuracy`), y = `Balanced Accuracy`, fill = runtime)) + 
  geom_bar(stat = "identity") +
  theme_bw(base_size = 16) + 
  labs(title = "Balanced Accuracy on Test Data",
       caption = "Higher balanced accuracy is better\nGreener bars indicate faster fitting models",
       fill = "Runtime (s)",
       y = "Balanced Accuracy",
       x = "Model") +
  scale_y_continuous(limits = c(0, 1)) +
  scale_fill_gradient(low = "green", 
                      high = "red")



# takes top (4) terms from best model, runs regression, shows effect plots

# varImp(dat.train.rpart, scale = FALSE)
# get rid of metric = "splits" if best model isn't C5.0
# varImp(dat.train.C5.0, metric = "splits", scale = FALSE)

topTerms <- varImp(dat.train.C5.0, metric = "splits", scale = FALSE)$importance %>%
  mutate(term = rownames(varImp(dat.train.C5.0, metric = "splits", scale = FALSE)$importance)) %>%
  arrange(desc(Overall)) %>%
  head(4) %>%
  pull(term)
topTerms <- gsub("\\..*","",topTerms)
topTermsFormula <- paste("Churn ~", paste(topTerms, collapse = " + "))
varImp.mod <- glm(formula = topTermsFormula, data = datTest.processed, family = binomial)
Anova(varImp.mod)
plot(allEffects(varImp.mod), type = "response")


#####
