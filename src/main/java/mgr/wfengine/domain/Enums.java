package mgr.wfengine.domain;

public class Enums {
	public enum ActivityDealType{
		/**
		 * 人工处理，等待，派待办
		 */
		Manual,
		/**
		 * 自动处理，不等待，不派待办
		 */
		Auto,
		/**
		 * 自动处理，等待，不派待办
		 */
		System
	}
	public enum ActivityInstanceState {
		Running,
		Finished,
		Rollbacked,
		/**
		 * 已经取消。通常发生在合并时，所有与此合并相关联的未完成的活动实例，会被设置为此状态
		 */
		Canceled, 
		Deleted
	}
	
	public enum FlowInstanceState{
		Running,
		Finished,
		Deleted,
		Canceled
	}
	
	public enum ActivityInstanceFinishState{
		Ok,
		Wait,
		MergeFinish,
		FlowInstanceFinish
	}
	
	public enum MergeInstanceState{
		Active,
		Merged,
		Rollbacked,
		Canceled
	}
	
	public enum ActivityDataState{
		Add,
		Edit,
		Rollbacked
	
	}
	
	public enum ExceptionPriority{
		Top,
		Medium,
		Low
	}
}
