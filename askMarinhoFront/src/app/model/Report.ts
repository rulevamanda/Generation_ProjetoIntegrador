import { Comment } from "./Comment"
import { Post } from "./Post"
import { User } from "./User"

export class Report {
    public idReport: number
    public userReport: User[]
    public postReport: Post
    public commentReport: Comment
}