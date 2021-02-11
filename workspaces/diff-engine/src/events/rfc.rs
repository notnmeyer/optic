use super::EventContext;
use cqrs_core::Event;
use serde::{Deserialize, Serialize};

// RFC Events
// -----------
#[derive(Deserialize, Debug, PartialEq, Serialize, Clone)]
pub enum RfcEvent {
  ContributionAdded(ContributionAdded),
  APINamed(APINamed),
  GitStateSet(GitStateSet),
  BatchCommitStarted(BatchCommitStarted),
  BatchCommitEnded(BatchCommitEnded),
}

#[derive(Deserialize, Debug, PartialEq, Serialize, Clone)]
#[serde(rename_all = "camelCase")]
pub struct ContributionAdded {
  id: String,
  key: String,
  value: String,
  event_context: Option<EventContext>,
}

#[derive(Deserialize, Debug, PartialEq, Serialize, Clone)]
#[serde(rename_all = "camelCase")]
pub struct APINamed {
  name: String,
  event_context: Option<EventContext>,
}

#[derive(Deserialize, Debug, PartialEq, Serialize, Clone)]
#[serde(rename_all = "camelCase")]
pub struct GitStateSet {
  branch_name: String,
  commit_id: String,
  event_context: Option<EventContext>,
}

#[derive(Deserialize, Debug, PartialEq, Serialize, Clone)]
#[serde(rename_all = "camelCase")]
pub struct BatchCommitStarted {
  pub batch_id: String,
  commit_message: String,
  pub parent_id: Option<String>,
  event_context: Option<EventContext>,
}

#[derive(Deserialize, Debug, PartialEq, Serialize, Clone)]
#[serde(rename_all = "camelCase")]
pub struct BatchCommitEnded {
  pub batch_id: String,
  event_context: Option<EventContext>,
}

impl Event for RfcEvent {
  fn event_type(&self) -> &'static str {
    match self {
      RfcEvent::ContributionAdded(evt) => evt.event_type(),
      RfcEvent::APINamed(evt) => evt.event_type(),
      RfcEvent::GitStateSet(evt) => evt.event_type(),
      RfcEvent::BatchCommitStarted(evt) => evt.event_type(),
      RfcEvent::BatchCommitEnded(evt) => evt.event_type(),
    }
  }
}

impl Event for ContributionAdded {
  fn event_type(&self) -> &'static str {
    "ContributionAdded"
  }
}

impl Event for APINamed {
  fn event_type(&self) -> &'static str {
    "APINamed"
  }
}

impl Event for GitStateSet {
  fn event_type(&self) -> &'static str {
    "GitStateSet"
  }
}

impl Event for BatchCommitStarted {
  fn event_type(&self) -> &'static str {
    "BatchCommitStarted"
  }
}

impl Event for BatchCommitEnded {
  fn event_type(&self) -> &'static str {
    "BatchCommitEnded"
  }
}
