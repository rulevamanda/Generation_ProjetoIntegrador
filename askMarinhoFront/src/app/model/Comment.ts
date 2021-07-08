import { Post } from "./Post"
import { User } from "./User"

export class Comment {
    public idComment: number
    public text: string
    public userComment: User
    public post: Post
    public userUpvoteComment: User[]
    public userReportComment: User[]
}